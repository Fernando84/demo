/* 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package lets.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LogSupport.
 */
public class LogSupport {

 /** The Constant logger. */
 public static final Logger logger = LoggerFactory.getLogger(LogSupport.class);

 /**
  * Checks if is trace enabled.
  *
  * @return true, if checks if is trace enabled
  */
 public static boolean isTraceEnabled() {
  return false;
 }

 /**
  * Trace.
  *
  * @param msg the msg
  */
 public static void trace(String msg) {
  logger.trace(msg);
 }

 /**
  * Trace.
  *
  * @param format the format
  * @param arg    the arg
  */
 public static void trace(String format, Object arg) {
  logger.trace(format, arg);

 }

 /**
  * Trace.
  *
  * @param format the format
  * @param arg1   the arg1
  * @param arg2   the arg2
  */
 public static void trace(String format, Object arg1, Object arg2) {
  logger.trace(format, arg1, arg2);
 }

 /**
  * Trace.
  *
  * @param format    the format
  * @param arguments the arguments
  */
 public static void trace(String format, Object... arguments) {
  logger.trace(format, arguments);
 }

 /**
  * Trace.
  *
  * @param msg the msg
  * @param t   the t
  */
 public static void trace(String msg, Throwable t) {
  logger.trace(msg, t);
 }

 /**
  * Checks if is debug enabled.
  *
  * @return true, if checks if is debug enabled
  */
 public static boolean isDebugEnabled() {
  return logger.isDebugEnabled();
 }

 /**
  * Debug.
  *
  * @param msg the msg
  */
 public static void debug(String msg) {
  logger.debug(msg);
 }

 /**
  * Debug.
  *
  * @param format the format
  * @param arg    the arg
  */
 public static void debug(String format, Object arg) {
  logger.debug(format, arg);

 }

 /**
  * Debug.
  *
  * @param format the format
  * @param arg1   the arg1
  * @param arg2   the arg2
  */
 public static void debug(String format, Object arg1, Object arg2) {
  logger.debug(format, arg1, arg2);

 }

 /**
  * Debug.
  *
  * @param format    the format
  * @param arguments the arguments
  */
 public static void debug(String format, Object... arguments) {
  logger.debug(format, arguments);

 }

 /**
  * Debug.
  *
  * @param msg the msg
  * @param t   the t
  */
 public static void debug(String msg, Throwable t) {
  logger.debug(msg, t);
 }

 /**
  * Checks if is info enabled.
  *
  * @return true, if checks if is info enabled
  */
 public static boolean isInfoEnabled() {
  return logger.isInfoEnabled();
 }

 /**
  * Info.
  *
  * @param msg the msg
  */
 public static void info(String msg) {
  logger.info(msg);
 }

 /**
  * Info.
  *
  * @param format the format
  * @param arg    the arg
  */
 public static void info(String format, Object arg) {
  logger.info(format, arg);

 }

 /**
  * Info.
  *
  * @param format the format
  * @param arg1   the arg1
  * @param arg2   the arg2
  */
 public static void info(String format, Object arg1, Object arg2) {
  logger.info(format, arg1, arg2);

 }

 /**
  * Info.
  *
  * @param format    the format
  * @param arguments the arguments
  */
 public static void info(String format, Object... arguments) {
  logger.info(format, arguments);

 }

 /**
  * Info.
  *
  * @param msg the msg
  * @param t   the t
  */
 public static void info(String msg, Throwable t) {
  logger.info(msg, t);
 }

 /**
  * Checks if is warn enabled.
  *
  * @return true, if checks if is warn enabled
  */
 public static boolean isWarnEnabled() {
  return logger.isWarnEnabled();
 }

 /**
  * Warn.
  *
  * @param msg the msg
  */
 public static void warn(String msg) {
  logger.warn(msg);
 }

 /**
  * Warn.
  *
  * @param format the format
  * @param arg    the arg
  */
 public static void warn(String format, Object arg) {
  logger.warn(format, arg);

 }

 /**
  * Warn.
  *
  * @param format the format
  * @param arg1   the arg1
  * @param arg2   the arg2
  */
 public static void warn(String format, Object arg1, Object arg2) {
  logger.warn(format, arg1, arg2);

 }

 /**
  * Warn.
  *
  * @param format    the format
  * @param arguments the arguments
  */
 public static void warn(String format, Object... arguments) {
  logger.warn(format, arguments);

 }

 /**
  * Warn.
  *
  * @param msg the msg
  * @param t   the t
  */
 public static void warn(String msg, Throwable t) {
  logger.warn(msg, t);
 }

 /**
  * Checks if is error enabled.
  *
  * @return true, if checks if is error enabled
  */
 public static boolean isErrorEnabled() {
  return logger.isErrorEnabled();
 }

 /**
  * Error.
  *
  * @param msg the msg
  */
 public static void error(String msg) {
  logger.error(msg);
 }

 /**
  * Error.
  *
  * @param e the e
  */
 public static void error(Throwable e) {
  logger.error("{}", CommonSupport.getStackTrace(e));
 }

 /**
  * Error.
  *
  * @param format the format
  * @param arg    the arg
  */
 public static void error(String format, Object arg) {
  logger.error(format, arg);

 }

 /**
  * Error.
  *
  * @param format the format
  * @param arg1   the arg1
  * @param arg2   the arg2
  */
 public static void error(String format, Object arg1, Object arg2) {
  logger.error(format, arg1, arg2);

 }

 /**
  * Error.
  *
  * @param format    the format
  * @param arguments the arguments
  */
 public static void error(String format, Object... arguments) {
  logger.error(format, arguments);

 }

 /**
  * Error.
  *
  * @param msg the msg
  * @param t   the t
  */
 public static void error(String msg, Throwable t) {
  logger.error(msg, t);
 }
 
}
