///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            p3
// Files:            MazeSolver.java MazeCell.java QueueADT.java ArrayQueue.java
// Semester:         CS302 Spring 2013
//
// Author:           Nick Stamas
// Email:            nstamas@wisc.edu
// CS Login:         stamas
// Lecturer's Name:  Jim Skrentny
// Lab Section:      N/A
//
//                   PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner:     Calvin Hareng
// Email:            hareng@wisc.edu
// CS Login:         hareng
// Lecturer's Name:  Jim Skrentny 
// Lab Section:      N/A
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          skeleton code poduced by TAs and/or professor
//////////////////////////// 80 columns wide //////////////////////////////////
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class is designed to read in a specified maze from the user and then solve it.
 * The user has the ability to display the maze, read in a new maze, solve the 
 * current loaded maze, and exit from the maze solver progrma
 * @author stamas and hareng
 *
 */
public class MazeSolver {

	//creates double maze array instance variable that will store the actual
	//maze.
	public static MazeCell[][] maze;

	//creates the ArrayQueue instance variable for the maze Queue. This queue
	//will be used to store the maze's route information.
	public static ArrayQueue<MazeCell> mazeQueue = new ArrayQueue<MazeCell>();

	//creates the Array List instance variable that will store the all the maze
	//cells that lead from start to the exit. The exit is at the front of the 
	//Array List and start is at the end.
	public static ArrayList<MazeCell> path;

	/**
	 * Main method which runs a loop that allows the user to perform the 
	 * abilities specified above. A command prompt is produced to allow the user
	 * to control the Maze Solver. They use d to display a loaded maze, m 
	 * "mazeName".txt to load a maze with the name specified, p to print to the
	 * instructions of a solved maze, and s to solve a maze.
	 * @param args
	 */
	public static void main(String[] args) {

		//Variable to store row of start cell.
		int startRow = 0;

		//Variable to store column of start cell.
		int startCol = 0;

		//Boolean variable that determines whether or not the current loaded 
		//maze has been solved.
		boolean solved = false;

		//Boolean variable that determines whether or not a maze has been loaded.
		boolean loaded = false;

		//Boolean variable that determines if the current maze has yet been 
		//attempted to be solved.
		boolean attempt = false;

		Scanner stdin = new Scanner(System.in);
		boolean done = false;
		while (!done) {
			System.out.print("Enter option - d, m, p, s, or x: ");
			String input = stdin.nextLine();

			if (input.length() > 0) {
				char choice = input.charAt(0);  // strip off option character
				String remainder = "";  // used to hold the remainder of input
				// trim off any leading or trailing spaces
				remainder = input.substring(1).trim();

				switch (choice) {

				case 'd':

					//checks if a maze was loaded, and if not says a maze
					//was not specified.
					if(!loaded){
						System.out.println("No maze specified");
					}

					//Others it moves throw the maze array and prints the 
					//loaded maze for the user.
					else{
						//for loop for rows of maze
						for(int x = 0; x < maze.length; x++){
							//for loop of columns of maze
							for(int y = 0; y < maze[0].length; y++){
								System.out.print(maze[x][y].toString());
							}
							//moves down after a entire row has been displayed
							System.out.println();
						}
					}	

					break;

				case 'm':
					String inFile = remainder;
					path = new ArrayList<MazeCell>();
					try{ // catch file not found exceptions

						//scanner used to read from the specified maze file
						Scanner in = new Scanner(new File(inFile));
						//takes in the number of rows as a string
						String num1 = in.next();
						//takes in the number of columns as a string
						String num2 = in.next();
						//moves to next line of the input file
						in.nextLine();
						//turns the sting form of number of rows and columns
						//into numbers.
						int rowNum = Integer.parseInt(num1.trim());
						int colNum = Integer.parseInt(num2.trim());
						//creates a double array to store the specified maze to
						//be loaded.
						maze = new MazeCell[rowNum][colNum];
						//clears the maze Queue since a new Maze has been loaded.
						mazeQueue = new ArrayQueue<MazeCell>();

						//Variable to count the number of rows
						int rowCount = 0;
						while(in.hasNextLine()){
							//takes in a line of the mazes
							String mazeLine = in.nextLine();
							//loops over the line taken in
							for(int k = 0; k < mazeLine.length(); k++){
								//gets the character at the specified spot.
								char type = mazeLine.charAt(k);
								//checks type of specified character. When the
								//type has been determined, that type will be
								//added to maze double array at the specified
								//position.
								switch (type) {
								case '|':
									MazeCell mazeWall = new MazeCell(
											MazeCell.WALL,rowCount,k);
									maze[rowCount][k] = mazeWall;
									break;
								case ' ':
									MazeCell mazeSpace = new MazeCell(
											MazeCell.OPEN,rowCount,k);
									maze[rowCount][k] = mazeSpace;
									boolean solution;	
									break;
								case 'S':
									MazeCell mazeStart = new MazeCell(
											MazeCell.START,rowCount,k);
									maze[rowCount][k] = mazeStart;
									break;
								case 'X':
									MazeCell mazeEnd = new MazeCell(
											MazeCell.END,rowCount,k);
									maze[rowCount][k] = mazeEnd;
									break;
								}
							}
							//increments row count and moves to the next row to
							//be taken in by the scanner.
							rowCount++;
						}
						//sets the variable to true since a maze has been 
						//properly loaded.
						loaded = true;
						//Since a new maze has been loaded, it has not yet been
						//attempted to be solved by the user.
						attempt = false;
						//catches file not found exception when loading a file.
					}catch(FileNotFoundException e){
						System.out.println("Invalid File");


					}
					break;

				case 'p':
					//checks if a maze has been loaded.
					if(!loaded){
						System.out.println("No maze specified.");
					}
					//checks if the current maze has been attempted.
					else if(!attempt){
						System.out.println("Maze has not yet been solved.");
					}
					//checks if the current maze has a solution.
					else if(!solved){
						System.out.println("No solution.");
					}
					//displays the proper solutions to the current maze.
					else{
						for(int i = path.size() - 1; i >= 0; i--){
							System.out.println("(" + path.get(i).row() + "," 
									+ path.get(i).col() + ")" );
						}
					}
					break;

				case 's':
					if(loaded){
						//sets attempt to true since the solve function has been
						//called.
						attempt = true;
						//creates a current cell variable.
						MazeCell current;
						//sets solved to false, since it has not yet been solved.
						solved = false;
						//creates new Array List to the shortest path to the exit.
						path = new ArrayList<MazeCell>();

						//for loop to locate the starting position.
						for(int i = 0; i < maze.length; i++){
							for(int j = 0; j < maze[0].length; j++){
								if(maze[i][j].type() == MazeCell.START){
									startRow = i;
									startCol = j;
									break; //Only breaks from 1st for loop
								}
							}
						}
						//enqueues the starting cell.
						mazeQueue.enqueue(maze[startRow][startCol]);

						// If maze is empty
						if(mazeQueue.isEmpty()){
							System.out.println("No maze specified.");
						}

						else{
							//creates loop to keep the solve funtions running
							//until a conclusion has been found.
							while(!mazeQueue.isEmpty()){
								//dequeues the first cell in the queue.
								current = mazeQueue.dequeue();
								//if the current is the end then a solution has 
								//been found
								if(current.type() == MazeCell.END){
									solved = true;
									System.out.println("Solution found.");
								}

								//checks if current cell has a crumb
								if(!solved && !current.hasCrumb()){
									//check to make sure their is a north 
									//neighbor of the current cell.
									if(current.row() > 0){
										//grabs the north cell and checks if it
										//has a crumb or is a wall. If not it is
										//enqueued into the back of the queue.
										if(maze[current.row() - 1]
										        [current.col()].type() 
										        != MazeCell.WALL && 
										        !maze[current.row() - 1]
										              [current.col()].hasCrumb()){
											maze[current.row() - 1]
											     [current.col()].setPrev(current);
											mazeQueue.enqueue(maze
													[current.row() - 1]
													 [current.col()]);
										}
									}
									//checks to make sure there is an east 
									//neighbor of the current cell. Then the
									//same checks and operations are performed
									//as were for the north cell.
									if(current.col() < maze[0].length -1){
										if(maze[current.row()]
										        [current.col()+1].type() 
										        != MazeCell.WALL && 
										        !maze[current.row()]
										              [current.col()+1].hasCrumb()){
											maze[current.row()]
											     [current.col()+1].setPrev(current);
											mazeQueue.enqueue(maze[current.row()]
											                       [current.col()+1]);
										}
									}
									//checks to make sure there is a south
									//neighbor of the current cell. Then the
									//same checks and operations are performed
									//as were for the north cell.
									if(current.row() < maze.length -1){
										if(maze[current.row() + 1]
										        [current.col()].type() 
										        != MazeCell.WALL && 
										        !maze[current.row() + 1]
										              [current.col()].hasCrumb()){
											maze[current.row() + 1]
											     [current.col()].setPrev(current);
											mazeQueue.enqueue(maze[current.row() + 1]
											                       [current.col()]);
										}
									}
									//checks to make sure there is a west
									//neighbor of the current cell. Then the
									//same checks and operations are performed
									//as were for the north cell.
									if(current.col() > 0){
										if(maze[current.row()]
										        [current.col()-1].type() 
										        != MazeCell.WALL && !maze[current.row()]
										         [current.col()-1].hasCrumb()){
											maze[current.row()]
											     [current.col()-1].setPrev(current);
											mazeQueue.enqueue(maze[current.row()]
											                       [current.col()-1]);
										}
									}
									//drops a crumb in the current cell.
									current.dropCrumb();
								}
								//if a solution was found then the path is 
								//stored in an Array List.
								else if(solved){
									//Adds all cells in the path except for the
									//start one.
									while(current.type() != MazeCell.START){
										path.add(current);
										//Moves through the correct path cells
										//from end to start.
										current = current.getPrev();
									}
									//adds the start cell
									path.add(current);
									break;

								}

							}
							//if a solution could not be found.
							if(!solved){
								System.out.println("No solution.");
							}


						}
					}
					//if a maze was not specified.
					else{
						System.out.println("No maze specified.");
					}


					break;

				case 'x':
					System.out.println("Exit");
					done = true;
					break;

				default:
					System.out.println("Unknown command.");
					break;

				}
			}
		}
	}
}
