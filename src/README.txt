Author: James Vinnicombe
About: This is a simple whiteboard application that allows multiple users to draw on a shared whiteboard.

To create a new whiteboard, run the following:
java -jar CreateWhiteBoard.jar <serverIPAddress> <serverPort> <username>

To join an existing whiteboard, run the following:
java -jar JoinWhiteBoard.jar <serverIPAddress> <serverPort> <username>

Here's an example of some simple commands to get it started:
java -jar CreateWhiteBoard.jar localhost 4444 jack
java -jar JoinWhiteBoard.jar localhost 4444 jill
