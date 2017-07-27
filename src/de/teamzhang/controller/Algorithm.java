package de.teamzhang.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import de.teamzhang.model.CalculatedSchedule;
import de.teamzhang.model.Course;
import de.teamzhang.model.Prio;
import de.teamzhang.model.Program;
import de.teamzhang.model.Room;
import de.teamzhang.model.SimplePrio;
import de.teamzhang.model.SingleChoicePrio;
import de.teamzhang.model.StudentSettings;
import de.teamzhang.model.Teacher;
import de.teamzhang.model.User;

@Controller
public class Algorithm {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MongoTemplate mongoTemplate;

	//public MongoTemplate mongoTemplate {
	//	return mongoTemplate;
	//}

	//  public Mongo mongo() throws Exception {
	//      MongoClientURI mcu = new MongoClientURI("mongodb://test:test@localhost/test");
	//      return new MongoClient(mcu);
	//  }
	//
	//  public MongoTemplate mongoTemplate {
	//      MongoTemplate mt = null;
	//      try {
	//          mt = new MongoTemplate(mongo(), "test");
	//      } catch (Exception e1) {
	//          // TODO Auto-generated catch block
	//          e1.printStackTrace();
	//      }
	//      try {
	//          mongo().getUsedDatabases();
	//      } catch (Exception e) {
	//          // TODO Auto-generated catch block
	//          e.printStackTrace();
	//      }
	//      return mt;
	//  }

	private int MINUSPOINTTHRESHOLD = 10;

	private int RANDOMGENERATIONMINUSPOINTSTHRESHOLD = 20000000;

	ArrayList<Course> allCourses = new ArrayList<Course>();
	ArrayList<Program> allPrograms = new ArrayList<Program>();
	ArrayList<Room> allRooms = new ArrayList<Room>();
	private List<Teacher> allTeachers = new ArrayList<Teacher>();
	private List<StudentSettings> allSettings = new ArrayList<StudentSettings>();
	private List<String> violatedPrios = new ArrayList<String>();

	private HttpServletRequest request;

	// private 
	ArrayList<Program> clonedPrograms;
	private Random randomGen = new Random();
	private int optimalThreshold = 0;
	private List<String> notOccupiedSlots = new ArrayList<String>();

	// 1. generate some testdata
	@RequestMapping(value = "/algorithm", method = RequestMethod.GET)
	public ModelAndView generateCalendar(HttpServletRequest request) {
		this.request = request;
		dropExistingSchedules();
		resetData();
		setPrograms();
		setTeachers();
		checkCourseSplit();
		setRooms();
		setStudentPrios();
		addTeachersToCourses();
		addCoursesToPrograms();
		weightPrios();
		int minusPoints = 0;
		int count = 0;
		int minusPointsThreshold = 800;
		long startTime = System.currentTimeMillis();
		int totalHits = 0;
		int bestPoints = 1000000;
		long avgTime;
		long totalTime;
		int totalCount = 0;
		//int totalPoints = 0;*/
		//do {
		StringBuilder sb = new StringBuilder();
		do {
			reset();
			/*try {
				cloneData();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			calculateRandomSchedule();
			boolean hillclimbingReached = false;
			minusPoints = getMinusPoints();
			if (minusPoints < RANDOMGENERATIONMINUSPOINTSTHRESHOLD) {
				count++;

				hillclimbingReached = true;

				//System.out.println("Minuspoints: " + minusPoints);
				for (int i = 30; i >= 0; i -= 10) {
					climbHill(i);
					//minusPoints = getMinusPoints();
				}
				minusPoints = getMinusPoints();
				//System.out.println(minusPoints);
				/*//System.out.println("Minuspoints after hillclimbing with threshold 100: " + minusPoints);
				if (minusPoints < minusPointsThreshold) {
					break;
				}
				
				climbHill(10);
				minusPoints = getMinusPoints();
				//System.out.println("Minuspoints after hillclimbing with threshold 10: " + minusPoints);
				if (minusPoints < minusPointsThreshold) {
					break;
				}
				
				climbHill(5);
				minusPoints = getMinusPoints();
				//System.out.println("Minuspoints after hillclimbing with threshold 5: " + minusPoints);
				if (minusPoints < minusPointsThreshold) {
					break;
				}*/
			}

			if (!hillclimbingReached && count % 10000 == 0) {
				// TODO
				// inspectTeachers();
				// inspectStudentPrios();
				RANDOMGENERATIONMINUSPOINTSTHRESHOLD += 1000;
				System.out.println("Iteration " + count + ", new threshold for random generation: "
						+ RANDOMGENERATIONMINUSPOINTSTHRESHOLD);
			}
			if (count % 200 == 0) {
				minusPointsThreshold *= 1.1;
				System.out.println("Iterations over " + count + ". New minuspoint-threshold: " + minusPointsThreshold);
			}
		} while (minusPoints > minusPointsThreshold);
		sb.append(
				"Done! Generated a schedule with " + minusPoints + " minuspoints. It took " + count + " iterations and "
						+ ((System.currentTimeMillis() - startTime) / 1000.0) + " seconds to create it.\\n");
		System.out.println("Done! Generated a schedule with " + minusPoints + " minuspoints. It took " + count
				+ " iterations and " + ((System.currentTimeMillis() - startTime) / 1000.0) + " seconds to create it.");
		for (Program p : allPrograms) {
			System.out.println(p.getName() + " has " + p.getProgramMinusPoints() + " points.");
			sb.append(p.getName() + " has " + p.getProgramMinusPoints() + " points.\\n");
		}
		System.out.println("The following teacher wishes had to be violated to create this schedule:");
		sb.append("The following teacher wishes had to be violated to create this schedule:\\n");
		for (Teacher t : allTeachers) {
			if (!t.getViolatedConditions().isEmpty())
				for (String s : t.getViolatedConditions()) {
					System.out.println(
							s + " for teacher " + t.getFirstName() + " " + t.getLastName() + ". Added 10 points.\\n");
					sb.append(s + " for teacher " + t.getFirstName() + " " + t.getLastName());
				}
		}
		minusPointsThreshold = 1000;
		//totalCount += count;
		//totalPoints += minusPoints;
		count = 0;
		//if (minusPoints < bestPoints)
		//bestPoints = minusPoints;
		//} while (totalHits < 100);
		totalTime = System.currentTimeMillis() - startTime;
		//avgTime = totalTime / 100;
		/*System.out.println("100 creations took with an average amount of " + totalPoints / 100 + " points took "
		        + totalTime / 1000.0 + " seconds and " + totalCount + " total tries.\n Average time was "
		        + avgTime / 1000.0 + ", average tries per hit " + totalCount / 100 + "." + " Best hit was " + bestPoints
		        + ".");*/

		for (Program p : allPrograms) {
			StringBuilder builder = new StringBuilder();
			int[][] board = new int[5][7];
			// boolean[][] isTeaching = t.getFullSlots();
			// builder.append(";Montag;Dienstag;Mittwoch;Donnerstag;Freitag\n");
			CalculatedSchedule cs = new CalculatedSchedule();
			cs.setProgramName(p.getName());
			for (int i = 0; i < board.length; i++)// for each row
			{
				for (int j = 0; j < board[0].length; j++)// for each column
				{
					// if (j == 0)
					// builder.append("Zeit " + i + ";");
					boolean isCourse = false;
					for (Course c : p.getCourses()) {
						if (c.getTime() == j && c.getDay() == i) {
							builder.append(c.getName() + ", " + c.getTeacher().getLastName() + ", "
									+ c.getRoom().getName() + ", " + c.getRoom().getType() + ", " + c.getSlotsNeeded()
									+ " Doppelstunde(n)" + ", Punkte: "
									+ c.getTeacher().getWeightedDayTimeWishes()[i][j]);
							cs.setFullSlot(c.getDay(), c.getTime(),
									c.getName() + ", " + c.getTeacher().getLastName() + ", " + c.getRoom().getName()
											+ ", " + c.getRoom().getType() + ", " + c.getSlotsNeeded()
											+ " Doppelstunde(n)" + ", Punkte: "
											+ c.getTeacher().getWeightedDayTimeWishes()[i][j]);
							if (c.getSlotsNeeded() == 2) {
								builder.append(c.getName() + ", " + c.getTeacher().getLastName() + ", "
										+ c.getRoom().getName() + ", " + c.getRoom().getType() + ", "
										+ c.getSlotsNeeded() + " Doppelstunde(n)" + ", Punkte: "
										+ c.getTeacher().getWeightedDayTimeWishes()[i][j + 1]);
								cs.setFullSlot(c.getDay(), c.getTime() + 1,
										c.getName() + ", " + c.getTeacher().getLastName() + ", " + c.getRoom().getName()
												+ ", " + c.getRoom().getType() + ", " + c.getSlotsNeeded()
												+ " Doppelstunde(n)" + ", Punkte: "
												+ c.getTeacher().getWeightedDayTimeWishes()[i][j + 1]);
								j++;
							}
						}
						isCourse = true;
					}
					if (!isCourse) {
						cs.setEmptySlot(i, j);
						builder.append("-" + "");
					}
					if ((i <= board.length - 1) && (j < board[0].length - 1))
						builder.append(";");
				}
				if (i < board.length - 1)
					builder.append("\n");// append new line at the end of the
										// row
			}
			try {
				if (!mongoTemplate.collectionExists("schedules")) {
					mongoTemplate.createCollection("schedules");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			mongoTemplate.insert(cs, "schedules");
			BufferedWriter writer;
			try {
				// File file = new File("\\WebContent\\resources\\" +
				// p.getName() + ".csv");
				File file = new File(p.getName() + ".csv");
				// file.getParentFile().mkdirs();
				writer = new BufferedWriter(new FileWriter(file));
				writer.write(builder.toString());
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*for (Teacher t : teachers.getTeachers().values()) {
		    StringBuilder builder = new StringBuilder();
		    int[][] board = t.getWeightedDayTimeWishes();
		    boolean[][] isTeaching = t.getFullSlots();
		    for (int i = 0; i < board.length; i++)// for each row
		    {
		        for (int j = 0; j < board[i].length; j++)// for each column
		        {
		            if (isTeaching[i][j])
		                builder.append(board[i][j] + "");// append to the output
		                                                // string
		            else
		                builder.append("0" + "");// append to the output string
		            if (j < board[i].length - 1)// if this is not the last row
		                                            // element
		                builder.append(",");// then add comma (if you don't like
		            // commas you can use spaces)
		        }
		        builder.append("\n");// append new line at the end of the row
		    }
		    BufferedWriter writer;
		    try {
		        writer = new BufferedWriter(new FileWriter(t.getLastName() + ".csv"));
		        writer.write(builder.toString());
		        writer.close();
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		
		}*/

		// TODO: stundenten miteinberechnen?
		// TODO: stundenplaene verbessern - bewerten und neu berechnen
		// TODO: stundenplaene darstellen

		// printMap(slots.getSlots());

		optimalThreshold = 700;
		DBCollection schedules = mongoTemplate.getCollection("schedules");
		DBCursor cursor = schedules.find();
		JSON json = new JSON();
		String serialize = json.serialize(cursor);
		System.out.println(serialize);
		ModelAndView modelandview = new ModelAndView("algoSuccess");
		modelandview.addObject("schedules", serialize);
		modelandview.addObject("info", sb.toString());
		return modelandview;
	}

	private void checkCourseSplit() {
		for (Teacher t : allTeachers) {
			for (Prio p : t.getPrios()) {
				if (p instanceof SimplePrio) {
					for (Course c : allCourses) {
						if ((p.isValidForAllCourses() || c.getCourseID() == p.getCourse()) && c.getSlotsNeeded() == 2) {
							c.setSlotsNeeded(1);
							Course newCourse = new Course();
							newCourse.setCourseID(c.getCourseID());
							newCourse.setName(c.getName());
							newCourse.setSlotsNeeded(1);
							newCourse.setTeacher(t);
							newCourse.setProgram(c.getProgram());
							newCourse.setSemester(c.getSemesters());
							newCourse.setSet(false);
						}
					}
				}
			}
		}

	}

	private void addCoursesToPrograms() {
		for (Course c : allCourses) {
			if (c.getSlotsNeeded() == 3)
				c.setSlotsNeeded(2);
			List<Integer> programs = c.getSemesters();
			for (int i : programs) {
				try {
					//System.out.println(c.getName());
					allPrograms.get(i - 1).addCourse(c);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void updateMissingData() {
		List<Teacher> allTeachers = new ArrayList<Teacher>();
		List<User> allUsers = new ArrayList<User>();
		DBCollection teachersDB = mongoTemplate.getCollection("teachers");
		DBCursor cursor = teachersDB.find();
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			Teacher t = mongoTemplate.getConverter().read(Teacher.class, obj);
			allTeachers.add(t);
		}
		teachersDB.drop();
		for (Teacher t : allTeachers) {
			String firstLetter = t.getFirstName().substring(0, 1);
			User user = new User();
			user.setFirstName(t.getFirstName());
			user.setLastName(t.getLastName());
			user.setPassword(passwordEncoder.encode("test"));
			String mail = firstLetter + "." + t.getLastName() + "@webmail.htw-berlin.de";
			user.setMail(mail);
			user.setUsername(mail);
			if (t.isProf())
				user.setRole(0);
			else
				user.setRole(1);
			t.setUser(user);
			allUsers.add(user);
		}
		DBCollection users = mongoTemplate.getCollection("user");
		users.drop();
		try {
			if (!mongoTemplate.collectionExists("teachers")) {
				mongoTemplate.createCollection("teachers");
			}
			if (!mongoTemplate.collectionExists("user")) {
				mongoTemplate.createCollection("user");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Teacher t : allTeachers) {
			mongoTemplate.insert(t, "teachers");
		}
		for (User t : allUsers) {
			mongoTemplate.insert(t, "user");
		}
	}

	private void resetData() {
		allCourses.clear();
		allPrograms.clear();
		allTeachers.clear();
		allSettings.clear();
	}

	private void dropExistingSchedules() {
		DBCollection schedules = mongoTemplate.getCollection("schedules");
		schedules.drop();
	}

	private void addTeachersToCourses() {
		for (Teacher t : allTeachers) {
			for (Course c : t.getCourses()) {
				//replace non valid characters
				try {

					c.setName(c.getName().replace("\\uFFFD", ""));
					allCourses.add(c);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		for (Teacher t : allTeachers) {
			for (Course c : t.getCourses()) {
				c.setName(c.getName().replace("\\uFFFD", ""));
				c.setTeacher(t);
			}
		}

	}

	private void setPrograms() {

		Program ba1 = new Program();
		ba1.setName("BachelorIMI1");
		ba1.setType(0);
		ba1.setSemester(1);
		allPrograms.add(ba1);

		Program ba2 = new Program();
		ba2.setName("BachelorIMI2");
		ba2.setType(0);
		ba2.setSemester(2);
		allPrograms.add(ba2);

		Program ba3 = new Program();
		ba3.setName("BachelorIMI3");
		ba3.setType(0);
		ba3.setSemester(3);
		allPrograms.add(ba3);

		Program ba4 = new Program();
		ba4.setName("BachelorIMI4");
		ba4.setType(0);
		ba4.setSemester(4);
		allPrograms.add(ba4);

		Program ba5 = new Program();
		ba5.setName("BachelorIMI5");
		ba5.setType(0);
		ba5.setSemester(5);
		allPrograms.add(ba5);

		Program ba6 = new Program();
		ba6.setName("BachelorIMI6");
		ba6.setType(0);
		ba6.setSemester(6);
		allPrograms.add(ba6);

		Program ma1 = new Program();
		ma1.setType(1);
		ma1.setSemester(7);
		ma1.setName("MasterIMI1");
		allPrograms.add(ma1);

		Program ma2 = new Program();
		ma2.setType(1);
		ma2.setSemester(8);
		ma2.setName("MasterIMI2");
		allPrograms.add(ma2);

		Program ma3 = new Program();
		ma3.setType(1);
		ma3.setSemester(9);
		ma3.setName("MasterIMI3");
		allPrograms.add(ma3);

		Program ma4 = new Program();
		ma4.setType(1);
		ma4.setSemester(10);
		ma4.setName("MasterIMI4");
		allPrograms.add(ma4);

	}

	private void setRooms() {
		// String csvFile = "/Users/krawallmieze/code/TeamZhang/zhangbackend/WebContent/resources/data/rooms.csv"; //TODO: right filepath
		//BufferedReader reader = new BufferedReader(new InputStreamReader(
		//request.getSession().getServletContext().getResourceAsStream("/resources/json/veranstaltungen.json")));
		//String csvFile = "/resources/data/rooms.csv"; //TODO: right filepath
		BufferedReader br = null;
		String row = "";
		String separator = ",";

		try {

			br = new BufferedReader(new InputStreamReader(
					request.getSession().getServletContext().getResourceAsStream("/resources/data/rooms.csv")));
			while ((row = br.readLine()) != null) {

				String[] room = row.replace("Informatik,", "Informatik").split(separator);

				Room r = new Room();
				r.setName(room[0]);
				r.setType(room[3]);
				try {
					r.setSeat(Integer.parseInt(room[1]));
				} catch (NumberFormatException e) {
					r.setSeat(20);
				}
				allRooms.add(r);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void setStudentPrios() {
		DBCollection settingsDB = mongoTemplate.getCollection("settings");
		DBCursor cursor = settingsDB.find();
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			StudentSettings s = mongoTemplate.getConverter().read(StudentSettings.class, obj);
			allSettings.add(s);
		}
	}

	private void setTeachers() {
		DBCollection teachersDB = mongoTemplate.getCollection("teachers");
		DBCursor cursor = teachersDB.find();
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			Teacher t = mongoTemplate.getConverter().read(Teacher.class, obj);
			allTeachers.add(t);
		}

	}

	private void inspectTeachers() {
		System.out.println("Couldn't find a schedule with selected teacher-prios, inspecting teachers...");
		for (Teacher t : allTeachers) {
			System.out.println(t.getLastName() + " has " + t.getMinusPoints() + " minuspoints.");
		}
	}

	private void cloneData() throws IOException, ClassNotFoundException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(allPrograms);
		oos.flush();
		oos.close();
		bos.close();
		byte[] byteData = bos.toByteArray();

		ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
		clonedPrograms = (ArrayList<Program>) new ObjectInputStream(bais).readObject();
	}

	private void restoreData() throws IOException, ClassNotFoundException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(clonedPrograms);
		oos.flush();
		oos.close();
		bos.close();
		byte[] byteData = bos.toByteArray();

		reset();

		ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
		allPrograms = (ArrayList<Program>) new ObjectInputStream(bais).readObject();

		allCourses.clear();
		for (Program p : allPrograms) {
			//programs.update(p);
			for (Course c : p.getCourses()) {
				allCourses.add(c);
				//courses.update(c);
				//rooms.update(c.getRoom());
				Teacher t = c.getTeacher();
				Room r = c.getRoom();
				//teachers.update(t);
				for (Prio prio : t.getPrios()) {
					//prios.update(prio);
					//TODO: write schedule into DB or just return as string?
				}
			}
		}
	}

	private boolean climbHill(int threshold) {
		resetMinusPointsFromViolatedPrios();
		for (Program p : allPrograms) {
			for (Course c : p.getCourses()) {
				if (c.getTeacher().getWeightedDayTimeWishes()[c.getDay()][c.getTime()] > threshold) {
					Teacher teacher = c.getTeacher();

					Random r = new Random();
					int randomTime = 0;
					if (c.getSlotsNeeded() == 2)
						randomTime = r.nextInt(6);
					else
						randomTime = r.nextInt(7);
					int randomDay = r.nextInt(5);
					int iteration = 0;
					// TODO: use Course to determine
					// room type preferences
					String roomTypeNeeded = teacher.getRoomType(c);
					Room room = null;
					boolean setNew = true;
					while (programTimeOccupiedOrTeacherBelowThreshold(c, randomTime, randomDay, threshold, iteration,
							teacher) || (room = findAvailableRoom(randomDay, randomTime, roomTypeNeeded)) == null
							|| (teacher.priosDontFit(randomDay, randomTime, false) && iteration < 300)
							|| (settingsViolated(c, randomDay, randomTime) && iteration < 1000)
							|| getNewProgramMinusPoints(p, randomDay, randomTime) > threshold * 2 && iteration < 300) {
						iteration++;
						randomTime = r.nextInt(7);
						randomDay = r.nextInt(5);
						if (c.getSlotsNeeded() == 2 && randomTime == 6)
							randomTime -= 1;
						if (iteration > 900) {
							setNew = false;
							break;
						}
					}
					if (setNew) {
						// add 10 points for every violated condition
						if (teacher.priosDontFit(randomDay, randomTime, true)) {
							teacher.addMinusPoints(teacher.getViolatedConditions().size() * 10);
						}
						teacher.setFreeSlot(c.getDay(), c.getTime());
						teacher.removeMinusPoints(teacher.getWeightedDayTimeWishes()[c.getDay()][c.getTime()]);
						//p.setFreeSlot(c.getDay(), c.getTime());
						setProgramsFreeSlot(c, c.getDay(), c.getTime());
						if (c.getSlotsNeeded() == 2) {
							teacher.setFreeSlot(c.getDay(), c.getTime() + 1);
							//p.setFreeSlot(c.getDay(), c.getTime() + 1);
							setProgramsFreeSlot(c, c.getDay(), c.getTime() + 1);
							teacher.removeMinusPoints(teacher.getWeightedDayTimeWishes()[c.getDay()][c.getTime() + 1]);
						}
						//if (c.getSlotsNeeded() == 2 && randomTime == 6)
						//  randomTime -= 1;

						//p.setFullSlot(randomDay, randomTime);
						setProgramsFullSlot(c, randomDay, randomTime);

						c.setDay(randomDay);
						c.setTime(randomTime);
						teacher.setFullSlot(randomDay, randomTime);
						teacher.addMinusPoints(teacher.getWeightedDayTimeWishes()[randomDay][randomTime]);
						if (c.getSlotsNeeded() == 2) {
							teacher.addMinusPoints(teacher.getWeightedDayTimeWishes()[randomDay][randomTime + 1]);
							teacher.setFullSlot(randomDay, randomTime + 1);
							//p.setFullSlot(randomDay, randomTime + 1);
							setProgramsFullSlot(c, randomDay, randomTime + 1);
						}
						room.setOccupied(randomDay, randomTime);
						c.setRoom(room);
						c.setSet(true);
					}
				}
			}

		}

		/*for (Teacher t : allTeachers) {
			if (!t.getViolatedConditions().isEmpty())
				t.addMinusPoints(t.getViolatedConditions().size() * 10);
		}*/
		for (Program p : allPrograms) {
			p.resetMinusPoints();
			addStudentMinusPoints(p);
		}
		return true;
	}

	private void resetMinusPointsFromViolatedPrios() {
		for (Teacher t : allTeachers) {
			if (!t.getViolatedConditions().isEmpty())
				t.removeMinusPoints(t.getViolatedConditions().size() * 10);
			t.getViolatedConditions().clear();
		}
	}

	private int getNewProgramMinusPoints(Program p, int day, int time) {
		p.setFullSlot(day, time);
		addStudentMinusPoints(p);
		int minusPoints = p.getProgramMinusPoints();
		p.setFreeSlot(day, time);
		p.resetMinusPoints();
		return minusPoints;
	}

	private boolean settingsViolated(Course c, int randomDay, int randomTime) {
		List<Integer> semesters = c.getSemesters();
		List<Program> programs = new ArrayList<Program>();
		boolean isViolated = false;
		for (int i : semesters)
			programs.add(allPrograms.get(i - 1));
		for (Program p : programs) {
			for (StudentSettings s : allSettings) {
				if (s.getProgram().equals("IMI-B") && p.getType() == 0
						|| s.getProgram().equals("IMI-M") && p.getType() == 1) {
					if (s.getMinusPoints() == 10000) {
						if (s.getType() == 0) {
							isViolated = checkIfMaxHoursPerDaySettingIsViolated(p, randomDay, randomTime, s);
							if (isViolated)
								return true;
						}
						if (s.getType() == 1) {
							isViolated = checkIfMaxBreakSettingIsViolated(p, randomDay, randomTime, s);
							if (isViolated)
								return true;
						}
						if (s.getType() == 2) {
							isViolated = checkIfMaxDaysSettingIsViolated(p, randomDay, randomTime, s);
							if (isViolated)
								return true;
						}
					}
				}
			}
		}
		return isViolated;
	}

	private boolean checkIfMaxDaysSettingIsViolated(Program p, int day, int theTime, StudentSettings s) {
		int studentMaxDaysValue = s.getValue();
		boolean[][] fullSlots = p.getFullSlots();
		fullSlots[day][theTime] = true;
		boolean[] daysBusy = new boolean[5];
		for (int days = 0; days < fullSlots.length; days++) {
			for (int time = 0; time < fullSlots[days].length; time++) {
				if (fullSlots[days][time] == true)
					daysBusy[days] = true;
			}
		}
		int count = 0;
		for (boolean b : daysBusy)
			if (b == true)
				count++;
		if (count > studentMaxDaysValue) {
			fullSlots[day][theTime] = false;
			return true;
		}
		fullSlots[day][theTime] = false;
		return false;
	}

	private boolean checkIfMaxHoursPerDaySettingIsViolated(Program p, int day, int theTime, StudentSettings s) {
		int studentMaxHoursValue = s.getValue();
		boolean[][] fullSlots = p.getFullSlots();
		fullSlots[day][theTime] = true;
		int count = 0;
		for (int time = 0; time < fullSlots[day].length; time++) {
			if (fullSlots[day][time] == true) {
				count++;
				if (count >= studentMaxHoursValue) {
					fullSlots[day][theTime] = false;
					return true;
				}
			}

		}

		fullSlots[day][theTime] = false;
		return false;
	}

	private boolean programTimeOccupiedOrTeacherBelowThreshold(Course c, int randomTime, int randomDay, int threshold,
			int iteration, Teacher teacher) {

		if (teacher.getFullSlots()[randomDay][randomTime] == true)
			return true;

		if (c.getSlotsNeeded() == 2 && teacher.getFullSlots()[randomDay][randomTime + 1] == true)
			return true;

		if (c.getTeacher().getWeightedDayTimeWishes()[randomDay][randomTime] > threshold)
			return true;

		List<Integer> semesters = c.getSemesters();
		for (int sem : semesters) {
			for (Program p : allPrograms) {
				if (p.getSemester() == sem) {
					if (p.isTimeOccupied(randomTime, randomDay) && c.getSlotsNeeded() == 1)
						return true;

					if (c.getSlotsNeeded() == 2 && p.isTimeOccupied(randomTime + 1, randomDay))
						return true;
				}
			}

		}

		return false;
	}

	private Room findAvailableRoom(int randomDay, int randomTime, String roomTypeNeeded) {
		boolean isSet = false;
		while (!isSet) {
			Room r = allRooms.get(randomGen.nextInt(allRooms.size() - 1));
			if (!roomTypeNeeded.equals("none") && !r.getType().equals(roomTypeNeeded)) {
				continue;
			}

			if (r.isAvailable(randomDay, randomTime)) {
				return r;
			}
		}
		return null;
	}

	private void reset() {
		for (Teacher t : allTeachers) {
			t.resetSchedule();
			t.resetMinuspoints();
		}
		for (Course c : allCourses)
			c.setSet(false);
		for (Program p : allPrograms) {
			p.resetFullSlots();
			p.resetMinusPoints();
		}
		for (Room r : allRooms) {
			r.resetOccupied();
		}
		violatedPrios.clear();
	}

	private int getMinusPoints() {
		int minusPoints = 0;
		for (Teacher t : allTeachers) {
			/*int[][] weightedDayTimeWishes = t.getWeightedDayTimeWishes();
			boolean[][] isTeaching = t.getFullSlots();
			for (int days = 0; days < weightedDayTimeWishes.length; days++) {
				for (int time = 0; time < weightedDayTimeWishes[days].length; time++) {
					if (isTeaching[days][time])
						minusPoints += weightedDayTimeWishes[days][time];
				}
			}*/
			if (!t.getViolatedConditions().isEmpty())
				t.addMinusPoints(t.getViolatedConditions().size() * 10);
			minusPoints += t.getMinusPoints();
		}
		for (Program p : allPrograms) {
			minusPoints += p.getProgramMinusPoints();
		}
		return minusPoints;
	}

	private void weightPrios() {
		// avoid ConcurentModi Exception

		for (Teacher t : allTeachers) {
			weightSingleSchedule(t);
			//teachers.update(t);
		}

	}

	private void weightSingleSchedule(Teacher t) {
		// 3 = auf keinen fall
		// 0 = top
		/*for (Prio p : t.getPrios()) {
			if (p instanceof Schedule) {
				int[] test = ((Schedule) p).getSchedule();
				t.setWeightedDayTimeWishes(test);
			}
		}*/
		t.calculateWeightedDayTimeWishes();
		int[][] weightedDayTimeWishes = t.getWeightedDayTimeWishes();

		//for (int days = 0; days < weightedDayTimeWishes.length; days++) {
		//for (int time = 0; time < weightedDayTimeWishes[days].length; time++) {

		for (Prio p : t.getPrios()) {

			if (p instanceof SingleChoicePrio) {
				if (p.getName().equals("Unterrichtsbeginn"))
					weightClassStart(t, (SingleChoicePrio) p);
			}
		}
		//}
		//}
		t.setWeightedDayTimeWishes(weightedDayTimeWishes);

	}

	// 1 minuspunkt wenn der teacher um 9 anfaengt und early start gewaehlt
	// hat, 2 bei 12 etc
	// genau anders bei later
	private void weightClassStart(Teacher t, SingleChoicePrio p) {
		boolean later = false;
		int[][] weightedDayTimeWishes = t.getWeightedDayTimeWishes();
		if (p.getOption() == 1)
			later = true; // teacher wants to start late
		for (int days = 0; days < weightedDayTimeWishes.length; days++) {
			for (int time = 0; time < weightedDayTimeWishes[days].length; time++) {
				if (later)
					weightedDayTimeWishes[days][time] += 6 - time;
				else
					weightedDayTimeWishes[days][time] += time;

			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void printMap(Map mp) {
		Iterator it = mp.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
		}
	}

	private int calculateRandomSchedule() {
		notOccupiedSlots.clear();

		for (Program p : allPrograms) {
			for (Course c : p.getCourses()) {
				Teacher teacher = c.getTeacher();
				if (!c.isSet()) {

					Random r = new Random();
					int randomTime = r.nextInt(7);
					int randomDay = r.nextInt(5);
					if (c.getSlotsNeeded() == 2 && randomTime == 6)
						randomTime -= 1;
					int iteration = 0;
					// TODO: use Course to determine
					// room type preferences
					String roomTypeNeeded = teacher.getRoomType(c);
					Room room = null;

					while ((p.isTimeOccupied(randomTime, randomDay) && ((c.getSlotsNeeded() == 1
							|| c.getSlotsNeeded() == 2 && p.isTimeOccupied(randomTime + 1, randomDay))))
							|| (room = findAvailableRoom(randomDay, randomTime, roomTypeNeeded)) == null
							|| (teacher.priosDontFit(randomDay, randomTime, false) && iteration < 300
									&& ((c.getSlotsNeeded() == 1 || c.getSlotsNeeded() == 2
											&& teacher.priosDontFit(randomDay, randomTime + 1, false))))
							|| ((settingsViolated(c, randomDay, randomTime) && iteration < 1000)
									&& ((c.getSlotsNeeded() == 1 || c.getSlotsNeeded() == 2
											&& settingsViolated(c, randomDay, randomTime + 1) && iteration < 1000)))) {
						if (iteration == 999)
							System.out.println("Prios cannot be satisfied for teacher " + teacher.getLastName());
						iteration++;
						randomTime = r.nextInt(7);
						randomDay = r.nextInt(5);
						if (c.getSlotsNeeded() == 2 && randomTime == 6)
							randomTime -= 1;
					}
					setProgramsFullSlot(c, randomDay, randomTime);
					//p.setFullSlot(randomDay, randomTime);
					teacher.addMinusPoints(teacher.getWeightedDayTimeWishes()[randomDay][randomTime]);
					c.setDay(randomDay);
					c.setTime(randomTime);
					teacher.setFullSlot(randomDay, randomTime);
					if (c.getSlotsNeeded() == 2) {
						teacher.setFullSlot(randomDay, randomTime + 1);
						//p.setFullSlot(randomDay, randomTime + 1);
						setProgramsFullSlot(c, randomDay, randomTime + 1);
						teacher.addMinusPoints(teacher.getWeightedDayTimeWishes()[randomDay][randomTime + 1]);
					}
					room.setOccupied(randomDay, randomTime);
					c.setRoom(room);
					c.setSet(true);
				}
			}

		}
		for (Program p : allPrograms)
			addStudentMinusPoints(p);
		return 0;

	}

	private void setProgramsFullSlot(Course c, int day, int time) {
		List<Integer> semesters = c.getSemesters();
		for (int sem : semesters) {
			for (Program p : allPrograms) {
				if (p.getSemester() == sem)
					p.setFullSlot(day, time);
			}
		}
	}

	private void setProgramsFreeSlot(Course c, int day, int time) {
		List<Integer> semesters = c.getSemesters();
		for (int sem : semesters) {
			for (Program p : allPrograms) {
				if (p.getSemester() == sem)
					p.setFreeSlot(day, time);
			}
		}
	}

	private void addStudentMinusPoints(Program p) {
		for (StudentSettings s : allSettings) {
			if (s.getProgram().equals("IMI-B") && p.getType() == 0
					|| s.getProgram().equals("IMI-M") && p.getType() == 1) {
				if (s.getType() == 0) {
					addMaxHoursMinusPoints(p, s);
				}
				if (s.getType() == 1) {
					addMaxBreakMinusPoints(p, s);
				}
				if (s.getType() == 2) {
					addMaxDaysMinusPoints(p, s);
				}
			}

		}
	}

	private void addMaxDaysMinusPoints(Program p, StudentSettings s) {
		int studentMaxDaysValue = s.getValue();
		int minusPointsToAdd = s.getMinusPoints();
		int totalMinusPoints = 0;

		boolean[][] fullSlots = p.getFullSlots();
		boolean[] daysBusy = new boolean[5];
		for (int days = 0; days < fullSlots.length; days++) {
			for (int time = 0; time < fullSlots[days].length; time++) {
				if (fullSlots[days][time] == true)
					daysBusy[days] = true;
			}
		}
		int count = 0;
		for (boolean b : daysBusy)
			if (b == true)
				count++;
		if (count > studentMaxDaysValue)
			totalMinusPoints += minusPointsToAdd;
		p.addMinusPoints(totalMinusPoints);
	}

	private void addMaxBreakMinusPoints(Program p, StudentSettings s) {
		int studentMaxBreakValue = s.getValue();
		int minusPointsToAdd = s.getMinusPoints();
		int totalMinusPoints = 0;
		boolean[][] fullSlots = p.getFullSlots();
		int count = 0;
		int max = 0;
		for (int days = 0; days < fullSlots.length; days++) {
			for (int time = 1; time < fullSlots[days].length; time++) {
				count = 0;
				if (fullSlots[days][time] == false && fullSlots[days][time - 1] == true) {
					while (time < fullSlots[days].length - 1 && fullSlots[days][time] == false) {
						count++;
						time++;
					}
					if (time >= 6 && fullSlots[days][time] == false)
						count = 0;
					if (max < count)
						max = count;
				}
			}
			if (max >= studentMaxBreakValue) {
				totalMinusPoints += minusPointsToAdd;
				break;
			}
		}
		p.addMinusPoints(totalMinusPoints);
	}

	private boolean checkIfMaxBreakSettingIsViolated(Program p, int day, int theTime, StudentSettings s) {
		int studentMaxBreakValue = s.getValue();
		boolean[][] fullSlots = p.getFullSlots();
		fullSlots[day][theTime] = true;
		int count = 0;
		int max = 0;
		for (int time = 1; time < fullSlots[day].length; time++) {
			count = 0;
			if (fullSlots[day][time] == false && fullSlots[day][time - 1] == true) {
				while (time < fullSlots[day].length - 1 && fullSlots[day][time] == false) {
					count++;
					time++;
				}
				if (time >= 6 && fullSlots[day][time] == false)
					count = 0;
				if (max < count)
					max = count;
			}
		}
		fullSlots[day][theTime] = false;
		if (max >= studentMaxBreakValue)
			return true;
		return false;
	}

	private void addMaxHoursMinusPoints(Program p, StudentSettings s) {
		int studentMaxHoursValue = s.getValue();
		int minusPointsToAdd = s.getMinusPoints();
		int totalMinusPoints = 0;
		try {
			boolean[][] fullSlots = p.getFullSlots();
			int count = 0;
			for (int days = 0; days < fullSlots.length; days++) {
				for (int time = 0; time < fullSlots[days].length; time++) {
					if (fullSlots[days][time] == true) {
						count++;
						if (count >= studentMaxHoursValue) {
							totalMinusPoints += minusPointsToAdd;
							break;
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		p.addMinusPoints(totalMinusPoints);
	}

	/*private  void addMaxBreakLengthMinusPoints(Program p) {
	    try {
	        int studentMaxBreakLengthValue = Integer.parseInt(p.getProp().getProperty("studentMaxBreakLengthValue"));
	        int studentMaxBreakLengthMinusPoints = Integer
	                .parseInt(p.getProp().getProperty("studentMaxBreakLengthMinusPoints"));
	        boolean[][] fullSlots = p.getFullSlots();
	        if (studentMaxBreakLengthMinusPoints > 0) {
	
	            for (int days = 0; days < fullSlots.length; days++) {
	                int count = 0;
	                int maxValue = 0;
	                for (int time = 0; time < fullSlots[days].length; time++) {
	                    if (fullSlots[days][time] == false && time > 0 && time < 7) {
	                        count++;
	                        if (count >= studentMaxBreakLengthValue) {
	                            p.addMinusPoints(studentMaxBreakLengthValue);
	                            count = 0;
	                            break;
	                        }
	                    } else
	                        count = 0;
	                }
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private  void addMaxDaysMinusPoints(Program p) {
	    try {
	        int studentMaxDaysValue = Integer.parseInt(p.getProp().getProperty("studentMaxDaysValue"));
	        int studentMaxDaysMinusPoints = Integer.parseInt(p.getProp().getProperty("studentMaxDaysMinusPoints"));
	        boolean[][] fullSlots = p.getFullSlots();
	        if (studentMaxDaysMinusPoints > 0) {
	            boolean[] daysBusy = new boolean[7];
	            for (int days = 0; days < fullSlots.length; days++) {
	                for (int time = 0; time < fullSlots[days].length; time++) {
	                    if (fullSlots[days][time] == true)
	                        daysBusy[days] = true;
	                }
	
	            }
	            int count = 0;
	            for (boolean b : daysBusy)
	                if (b == true)
	                    count++;
	            if (count >= studentMaxDaysValue)
	                p.addMinusPoints(studentMaxDaysMinusPoints);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private  void addMaxHoursMinusPoints(Program p) {
	
	
	    try {
	        int studentMaxHoursValue = Integer.parseInt(p.getProp().getProperty("studentMaxHoursValue"));
	        int studentMaxHoursMinusPoints = Integer.parseInt(p.getProp().getProperty("studentMaxHoursMinusPoints"));
	        boolean[][] fullSlots = p.getFullSlots();
	        if (studentMaxHoursMinusPoints > 0) {
	            for (int days = 0; days < fullSlots.length; days++) {
	                int count = 0;
	                for (int time = 0; time < fullSlots[days].length; time++) {
	                    if (fullSlots[days][time] == true) {
	                        count++;
	                        if (count >= studentMaxHoursValue) {
	                            p.addMinusPoints(studentMaxHoursValue);
	                            break;
	                        }
	                    }
	                }
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public  void generatePlan() {
	    // use and run above functions
	}*/

}
