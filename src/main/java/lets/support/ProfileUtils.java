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

import org.apache.commons.lang3.ArrayUtils;

/**
 * 
 * @author zhoupan.
 */
public class ProfileUtils {

 /** The Constant PROFILE_DEV. */
 public static final String PROFILE_DEV = "dev";

 /** The Constant PROFILE_TEST. */
 public static final String PROFILE_TEST = "test";

 /** The Constant PROFILE_PRODUCTION. */
 public static final String PROFILE_PRODUCTION = "production";

 /** The Constant PROFILE_PROD. */
 public static final String PROFILE_PROD = "prod";

 public static final String PROFILE_UAT = "uat";

 /** The active profiles. */
 private static String[] activeProfiles;

 /** The default profiles. */
 private static String[] defaultProfiles;

 /**
  * Gets the active profiles.
  * 
  * @return the active profiles
  */
 public static String[] getActiveProfiles() {
  return activeProfiles;
 }

 /**
  * Sets the active profiles.
  * 
  * @param activeProfiles the new active profiles
  */
 public synchronized static void setActiveProfiles(String[] activeProfiles) {
  LogSupport.info("setActiveProfiles:{}", CommonSupport.join(activeProfiles, "|"));
  ProfileUtils.activeProfiles = activeProfiles;
 }

 /**
  * Gets the default profiles.
  * 
  * @return the default profiles
  */
 public static String[] getDefaultProfiles() {
  return defaultProfiles;
 }

 /**
  * Sets the default profiles.
  * 
  * @param defaultProfiles the new default profiles
  */
 public synchronized static void setDefaultProfiles(String[] defaultProfiles) {
  LogSupport.info("setDefaultProfiles:{}", CommonSupport.join(defaultProfiles, "|"));
  ProfileUtils.defaultProfiles = defaultProfiles;
 }

 /**
  * Gets the profiles.
  *
  * @return activeProfiles or defaultProfiles or null.
  */
 public synchronized static String[] getProfiles() {
  if (ProfileUtils.activeProfiles != null && ProfileUtils.activeProfiles.length > 0) {
   return ProfileUtils.activeProfiles;
  }
  if (ProfileUtils.defaultProfiles != null && ProfileUtils.defaultProfiles.length > 0) {
   return ProfileUtils.defaultProfiles;
  }
  LogSupport.warn("not profile found.");
  return null;
 }

 /**
  * Contain profile.
  *
  * @param profile the profile
  * @return true, if successful
  */
 public static boolean containProfile(String profile) {
  if (ProfileUtils.activeProfiles != null && ArrayUtils.contains(ProfileUtils.activeProfiles, profile)) {
   return true;
  }
  if (ProfileUtils.defaultProfiles != null && ArrayUtils.contains(ProfileUtils.defaultProfiles, profile)) {
   return true;
  }
  return false;
 }

 /**
  * Checks if is dev.
  *
  * @return true, if is dev
  */
 public static boolean isDev() {
  return ProfileUtils.containProfile(ProfileUtils.PROFILE_DEV);
 }

 /**
  * Checks if is test.
  *
  * @return true, if is test
  */
 public static boolean isTest() {
  return ProfileUtils.containProfile(ProfileUtils.PROFILE_TEST);
 }

 /**
  * Checks if is uat.
  *
  * @return true, if is uat
  */
 public static boolean isUat() {
  return ProfileUtils.containProfile(ProfileUtils.PROFILE_UAT);
 }

 /**
  * Checks if is production.
  *
  * @return true, if is production
  */
 public static boolean isProduction() {
  return ProfileUtils.containProfile(ProfileUtils.PROFILE_PRODUCTION) || ProfileUtils.containProfile(ProfileUtils.PROFILE_PROD);
 }

 /**
  * Active dev.
  */
 public static void activeDev() {
  ProfileUtils.setActiveProfiles(new String[] { ProfileUtils.PROFILE_DEV });
 }

 /**
  * Active test.
  */
 public static void activeTest() {
  ProfileUtils.setActiveProfiles(new String[] { ProfileUtils.PROFILE_TEST });
 }

 /**
  * Active production.
  */
 public static void activeProduction() {
  ProfileUtils.setActiveProfiles(new String[] { ProfileUtils.PROFILE_PRODUCTION });
 }
}
