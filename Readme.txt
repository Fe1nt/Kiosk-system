JRE 1.7
jdk 1.7.0_45

How to launch this Java application:

1.Save the [Software_group065.zip] into your D:\(Must be D:\ ,the root folder).

2.Uncompress the zip and preserve its contents to the current folder, sothat a folder named [ticket-kiosk-1-7] is under the root directory of D:\.

3.Start the command line.

4.Type [javac -Xlint:unchecked -classpath "D:\ticket-kiosk-1-7\junit-4.12.jar";"D:\ticket-kiosk-1-7\hamcrest-core-1.3.jar"; kiosk\controller\*.java kiosk\model\*.java kiosk\view\*.java] in the command line to compile the application.

5.Type [java kiosk.controller.Main] in the command line to launch the application.

6.Done. 

For test£º
Type [java -classpath "D:\ticket-kiosk-1-7\junit-4.12.jar";"D:\ticket-kiosk-1-7\hamcrest-core-1.3.jar"; kiosk.controller.JunitRunner] after compiling.

*The text programs are in kiosk.controller package.


