# karaf-pax-jdbc
Problem with pool pax-jdbc 


Hi Friends,

I have a problem with the pool connections of pax-jdbc in karaf, I'm trying to inject a Mysql DataSource (DS) through 
blueprint.xml into my project, for test it, I have built a karaf command where injects the DS into karaf command class 
and execute a query with that connection. That it's OK, but the problem is when I execute the command a lot times, for 
each execution a new instance of the DS is created and the pool connection cannot open new connections to MySQL, because 
the pool had reached the limit.

I have uploaded my code to github in this link: https://github.com/christmo/karaf-pax-jdbc , you can give a pull request 
if you find an error in this project.

For test this project you can do:

	1. Download karaf 4.0.4 or apache-karaf-4.1.0-SNAPSHOT
	2. Copy the file karaf-pax-jdbc/etc/org.ops4j.datasource-my-ds.cfg to ${karaf}/etc, this file have the mysql 
	   configuration change with your mysql configuration data.
	4. Start mysql database engine
	3. Start karaf -> cd ${karaf}/bin/; ./karaf
	4. Add the repo of this project with this karaf command: feature:repo-add mvn:pax/features/1.0-SNAPSHOT/xml/features
	5. Install the feature created for this project: feature:install mysql-test
	6. Execute the command for test this problem: mysql-connection, this command only execute "Select 1" in mysql

If you execute 9 times this command "mysql-connection", it will freeze the prompt of karaf and if you interrupt the 
execution you can get this exception:

java.sql.SQLException: Cannot get a connection, general error
	at org.apache.commons.dbcp2.PoolingDataSource.getConnection(PoolingDataSource.java:146)
	at com.twim.OrmCommand.execute(OrmCommand.java:53)
	at org.apache.karaf.shell.impl.action.command.ActionCommand.execute(ActionCommand.java:83)
	at org.apache.karaf.shell.impl.console.osgi.secured.SecuredCommand.execute(SecuredCommand.java:67)
	at org.apache.karaf.shell.impl.console.osgi.secured.SecuredCommand.execute(SecuredCommand.java:87)
	at org.apache.felix.gogo.runtime.Closure.executeCmd(Closure.java:480)
	at org.apache.felix.gogo.runtime.Closure.executeStatement(Closure.java:406)
	at org.apache.felix.gogo.runtime.Pipe.run(Pipe.java:108)
	at org.apache.felix.gogo.runtime.Closure.execute(Closure.java:182)
	at org.apache.felix.gogo.runtime.Closure.execute(Closure.java:119)
	at org.apache.felix.gogo.runtime.CommandSessionImpl.execute(CommandSessionImpl.java:94)
	at org.apache.karaf.shell.impl.console.ConsoleSessionImpl.run(ConsoleSessionImpl.java:270)
	at java.lang.Thread.run(Thread.java:745)
Caused by: java.lang.InterruptedException
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.reportInterruptAfterWait(AbstractQueuedSynchronizer.java:2014)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2048)
	at org.apache.commons.pool2.impl.LinkedBlockingDeque.takeFirst(LinkedBlockingDeque.java:583)
	at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:442)
	at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:363)
	at org.apache.commons.dbcp2.PoolingDataSource.getConnection(PoolingDataSource.java:134)
	... 12 more
