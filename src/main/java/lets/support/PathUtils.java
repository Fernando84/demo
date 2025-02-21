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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

/**
 * PathUtils.
 * 
 * @author zhoupan
 */
public class PathUtils {

 /** The Constant WINDOWS_DRIVER_SEPARATOR. */
 public static final String WINDOWS_DRIVER_SEPARATOR = ":";

 /** The Constant EXTENSION_SEPARATOR. */
 public static final String EXTENSION_SEPARATOR = ".";

 /** The Constant WINDOWS_FOLDER_SEPARATOR. */
 public static final String WINDOWS_FOLDER_SEPARATOR = "\\";

 /** The Constant TOP_PATH. */
 public static final String TOP_PATH = "..";

 /** The Constant CURRENT_PATH. */
 public static final String CURRENT_PATH = ".";
 /** The Constant PATH_SEPARATOR. */
 public static final String PATH_SEPARATOR = "/";

 /**
  * Builds the virtual path.
  *
  * @param parts the parts
  * @return the string
  */
 public static String buildVirtualPath(String... parts) {
  StringBuilder path = new StringBuilder();
  for (int i = 0; i < parts.length; i++) {
   if (i > 0 && !StringUtils.endsWith(path.toString(), PATH_SEPARATOR)) {
    path.append(PATH_SEPARATOR);
   }
   String part = parts[i];
   part = StringUtils.replace(part, WINDOWS_FOLDER_SEPARATOR, PATH_SEPARATOR);
   if (StringUtils.startsWith(part, PATH_SEPARATOR)) {
    part = StringUtils.substringAfter(part, PATH_SEPARATOR);
   }
   path.append(part);
  }
  return path.toString();
 }

 /**
  * Builds the virtual path.
  *
  * @param startWithPathSeparator the start with path separator
  * @param parts                  the parts
  * @return the string
  */
 public static String buildVirtualPath(boolean startWithPathSeparator, String... parts) {
  String path = buildVirtualPath(parts);
  return path(path, startWithPathSeparator);
 }

 /**
  * Builds the virtual path start with path separator.
  *
  * @param parts the parts
  * @return the string
  */
 public static String buildVirtualPathStartWithPathSeparator(String... parts) {
  return buildVirtualPath(true, parts);
 }

 /**
  * Path.
  *
  * @param part the part
  * @return the string
  */
 public static String path(String part) {
  part = StringUtils.replace(part, WINDOWS_FOLDER_SEPARATOR, PATH_SEPARATOR);
  return part;
 }

 /**
  * Path.
  *
  * @param part                   the part
  * @param startWithPathSeparator the start with path separator
  * @return the string
  */
 public static String path(String part, boolean startWithPathSeparator) {
  part = StringUtils.replace(part, WINDOWS_FOLDER_SEPARATOR, PATH_SEPARATOR);
  if (startWithPathSeparator) {
   if (!part.startsWith(PATH_SEPARATOR)) {
    return PATH_SEPARATOR + part;
   }
  } else {
   if (StringUtils.startsWith(part, PATH_SEPARATOR)) {
    return StringUtils.substringAfter(part, PATH_SEPARATOR);
   }
  }
  return part;
 }

 /**
  * Gets the relative path.
  *
  * @param baseDir    the base dir
  * @param targetFile the target file
  * @return the relative path
  */
 public static String getRelativePath(File baseDir, File targetFile) {
  return StringUtils.substringAfter(path(targetFile.getAbsolutePath()), path(baseDir.getAbsolutePath()));
 }

 /**
  * Gets the relative path.
  *
  * @param baseDir    the base dir
  * @param targetFile the target file
  * @return the relative path
  */
 public static String getRelativePath(String baseDir, String targetFile) {
  return StringUtils.substringAfter(path(targetFile), path(baseDir));
 }

 /**
  * Gets the relative path between.
  *
  * @param base      the base
  * @param current   the current
  * @param reference the reference
  * @return the relative path between
  */
 public static String getRelativePathBetween(String base, String current, String reference) {
  StringBuilder urlBuilder = new StringBuilder();
  base = path(base);
  current = path(current);
  reference = path(reference);
  String relativeCurrent = StringUtils.substringAfter(current, base);
  int currentLevel = StringUtils.countMatches(relativeCurrent, PathUtils.PATH_SEPARATOR);
  String relativeReference = StringUtils.substringAfter(reference, base);
  int referencereLevel = StringUtils.countMatches(relativeReference, PathUtils.PATH_SEPARATOR);
  System.err.println("currentLevel:" + currentLevel + " relativeLevel:" + referencereLevel);
  System.err.println("current:" + current + " reference:" + reference);
  // 同级别.
  if (referencereLevel == currentLevel) {
   if (StringUtils.startsWith(relativeReference, PathUtils.PATH_SEPARATOR)) {
    urlBuilder.append(StringUtils.substringAfter(relativeReference, PathUtils.PATH_SEPARATOR));
   } else {
    urlBuilder.append(relativeReference);
   }
  }
  if (referencereLevel > currentLevel) {
   for (int level = 1; level < currentLevel; level++) {
    urlBuilder.append("../");
   }
   relativeReference = StringUtils.substringAfter(reference, StringUtils.substringBeforeLast(current, PathUtils.PATH_SEPARATOR));
   if (StringUtils.startsWith(relativeReference, PathUtils.PATH_SEPARATOR)) {
    urlBuilder.append(StringUtils.substringAfter(relativeReference, PathUtils.PATH_SEPARATOR));
   } else {
    urlBuilder.append(relativeReference);
   }
  }
  if (referencereLevel < currentLevel) {
   for (int level = 0; level < currentLevel - referencereLevel; level++) {
    urlBuilder.append("../");
   }
   relativeCurrent = StringUtils.substringAfter(current, StringUtils.substringBeforeLast(reference, PathUtils.PATH_SEPARATOR));
   if (StringUtils.startsWith(relativeCurrent, PathUtils.PATH_SEPARATOR)) {
    urlBuilder.append(StringUtils.substringAfter(relativeCurrent, PathUtils.PATH_SEPARATOR));
   } else {
    urlBuilder.append(relativeCurrent);
   }
  }
  return urlBuilder.toString();
 }

 /**
  * 包名转换成路径.
  *
  * @param packageName the package name
  * @return the string
  */
 public static String packageNameToPath(String packageName) {
  if (StringUtils.isBlank(packageName)) {
   return EXTENSION_SEPARATOR;
  } else {
   return StringUtils.replace(packageName, EXTENSION_SEPARATOR, PATH_SEPARATOR);
  }
 }

 /**
  * 判断指定的路径是否相对路径.
  *
  * @param path the path
  * @return true, if checks if is relative path
  */
 public static boolean isRelativePath(String path) {
  if (CommonSupport.contains(path, WINDOWS_DRIVER_SEPARATOR)) {
   return false;
  }
  if (CommonSupport.startsWithAny(path, new String[] { WINDOWS_FOLDER_SEPARATOR, PATH_SEPARATOR })) {
   return false;
  }
  if (CommonSupport.startsWithAny(path, new String[] { TOP_PATH, CURRENT_PATH })) {
   return true;
  }
  return true;
 }

 /**
  * Gets the current dir.
  *
  * @return the current dir
  */
 public static File getCurrentDir() {
  return new File(CURRENT_PATH);
 }

 /**
  * Gets the user home dir.
  *
  * @return the user home dir
  */
 public static File getUserHomeDir() {
  return SystemUtils.getUserHome();
 }

 /**
  * Gets the user home dir.
  *
  * @param workDir the work dir
  * @return the user home dir
  */
 public static File getUserHomeDir(String workDir) {
  File homeDir = getUserHomeDir();
  File dir = new File(homeDir, workDir);
  if (!dir.exists()) {
   dir.mkdirs();
  }
  return dir;
 }

 /**
  * Gets the user home dir.
  *
  * @param clasz the clasz
  * @return the user home dir
  */
 public static File getUserHomeDir(Class<?> clasz) {
  return getUserHomeDir(clasz.getName());
 }

 /**
  * Gets the user home dir.
  *
  * @param clasz   the clasz
  * @param workDir the work dir
  * @return the user home dir
  */
 public static File getUserHomeDir(Class<?> clasz, String workDir) {
  File homeDir = getUserHomeDir(clasz.getName());
  File dir = new File(homeDir, workDir);
  if (!dir.exists()) {
   dir.mkdirs();
  }
  return dir;
 }

 /**
  * Prefer work dir.
  *
  * @param workDirDefault the work dir default
  * @param workDir        the work dir
  * @return the file
  */
 public static File preferWorkDir(File workDirDefault, String workDir) {
  if (CommonSupport.isBlank(workDir)) {
   return workDirDefault;
  }
  File file = null;
  if (isRelativePath(workDir)) {
   file = new File(workDirDefault, workDir);
  } else {
   file = new File(workDir);
  }
  LoggerSupport.debug("preferWorkDir:workDirDefault={} workDir={} preferWorkDir={}", new Object[] { workDirDefault, workDir, file });
  if (!file.exists()) {
   file.mkdirs();
  }
  return file;
 }

 /**
  * Prefer current dir as work dir.
  *
  * @param workDir the work dir
  * @return the file
  */
 public static File preferCurrentDirAsWorkDir(String workDir) {
  return preferWorkDir(getCurrentDir(), workDir);
 }

 /**
  * Prefer current dir parent as work dir.
  *
  * @param workDir the work dir
  * @return the file
  */
 public static File preferCurrentDirParentAsWorkDir(String workDir) {
  return preferWorkDir(getCurrentDir().getParentFile(), workDir);
 }

 /**
  * Prefer user home dir as work dir.
  *
  * @param workDir the work dir
  * @return the file
  */
 public static File preferUserHomeDirAsWorkDir(String workDir) {
  return preferWorkDir(getUserHomeDir(), workDir);
 }

 /**
  * Url to file path.
  *
  * @param url        the http or https url
  * @param keepDomain the keep domain
  * @return the string
  */
 public static String urlToFilePath(String url, boolean keepDomain) {
  if (!keepDomain) {
   if (StringUtils.contains(url, "://")) {
    url = StringUtils.substringAfter(url, "://");
   }
   if (StringUtils.contains(url, "/")) {
    url = StringUtils.substringAfter(url, "/");
   } else {
    url = "";
   }
  }
  return StringUtils.replaceEachRepeatedly(url, new String[] { "://", "?", ":" }, new String[] { "@", "@", "@" });
 }

 /**
  * Url without query and hash.
  *
  * @param url the url
  * @return the string
  */
 public static String urlWithoutQueryAndHash(String url) {
  if (CommonSupport.contains(url, "?")) {
   url = CommonSupport.substringBefore(url, "?");
  }
  if (CommonSupport.contains(url, "#")) {
   url = CommonSupport.substringBefore(url, "#");
  }
  return url;
 }

 /**
  * Depart paths.
  *
  * @param url        the url
  * @param keepDomain the keep domain
  * @return the list< string>
  */
 public static List<String> departPaths(String url, boolean keepDomain) {
  if (!keepDomain) {
   if (StringUtils.contains(url, "://")) {
    url = StringUtils.substringAfter(url, "://");
   }
   if (StringUtils.contains(url, "/")) {
    url = StringUtils.substringAfter(url, "/");
   } else {
    url = "";
   }
  }
  url = path(url, false);
  String[] parts = StringUtils.split(url, PATH_SEPARATOR);
  List<String> paths = new ArrayList<>();
  for (String part : parts) {
   String path = CommonSupport.trimX(part, true, PATH_SEPARATOR);
   if (CommonSupport.isNotBlank(path)) {
    paths.add(path);
   }
  }
  return paths;
 }
}
