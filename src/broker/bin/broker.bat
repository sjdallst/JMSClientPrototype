@echo off
set ACTIVEMQ_HOME="C:/ActiveMQ/apache-activemq-5.10.0"
set ACTIVEMQ_BASE="C:/Users/sdallstream/workspace/JMSClientPrototype/src/broker"

set PARAM=%1
:getParam
shift
if "%1"=="" goto end
set PARAM=%PARAM% %1
goto getParam
:end

%ACTIVEMQ_HOME%/bin/activemq %PARAM%