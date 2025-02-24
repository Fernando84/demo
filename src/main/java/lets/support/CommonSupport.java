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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Pattern;
import jodd.bean.BeanUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.helpers.MessageFormatter;

/**
 * CommonSupport(from org.apache.commons). Preconditions(from google guava).
 * 
 */
public class CommonSupport {

  /**
   * <p>
   * Operations on <code>Object</code>.
   * </p>
   * 
   * <p>
   * This class tries to handle <code>null</code> input gracefully. An exception
   * will generally not be thrown for a <code>null</code> input. Each method
   * documents its behaviour in more detail.
   * </p>
   * 
   * <p>
   * #ThreadSafe#
   * </p>
   *
   * @author Apache Software Foundation
   * @author <a href="mailto:nissim@nksystems.com">Nissim Karpenstein</a>
   * @author <a href="mailto:janekdb@yahoo.co.uk">Janek Bogucki</a>
   * @author Daniel L. Rall
   * @author Gary Gregory
   * @author Mario Winterer
   * @author <a href="mailto:david@davidkarlsen.com">David J. M. Karlsen</a>
   * @version $Id: ObjectUtils.java 1057434 2011-01-11 01:27:37Z niallp $
   * @since 1.0
   */
  // @Immutable
  public static class ObjectUtils {

    /**
     * <p>
     * Singleton used as a <code>null</code> placeholder where <code>null</code> has
     * another meaning.
     * </p>
     *
     * <p>
     * For example, in a <code>HashMap</code> the
     * {@link java.util.HashMap#get(java.lang.Object)} method returns
     * <code>null</code> if the <code>Map</code> contains <code>null</code> or if
     * there is no matching key. The <code>Null</code> placeholder can be used to
     * distinguish between these two cases.
     * </p>
     *
     * <p>
     * Another example is <code>Hashtable</code>, where <code>null</code> cannot be
     * stored.
     * </p>
     *
     * <p>
     * This instance is Serializable.
     * </p>
     */
    public static final Null NULL = new Null();

    /**
     * <p>
     * <code>ObjectUtils</code> instances should NOT be constructed in standard
     * programming. Instead, the class should be used as
     * <code>ObjectUtils.defaultIfNull("a","b");</code>.
     * </p>
     *
     * <p>
     * This constructor is public to permit tools that require a JavaBean instance
     * to operate.
     * </p>
     */
    public ObjectUtils() {
      super();
    }

    // Defaulting
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Returns a default value if the object passed is <code>null</code>.
     * </p>
     * 
     * <pre>
     * ObjectUtils.defaultIfNull(null, null)      = null
     * ObjectUtils.defaultIfNull(null, "")        = ""
     * ObjectUtils.defaultIfNull(null, "zz")      = "zz"
     * ObjectUtils.defaultIfNull("abc", *)        = "abc"
     * ObjectUtils.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
     * </pre>
     *
     * @param object       the <code>Object</code> to test, may be <code>null</code>
     * @param defaultValue the default value to return, may be <code>null</code>
     * @return <code>object</code> if it is not <code>null</code>, defaultValue
     *         otherwise
     */
    public static Object defaultIfNull(Object object, Object defaultValue) {
      return object != null ? object : defaultValue;
    }

    /**
     * <p>
     * Compares two objects for equality, where either one or both objects may be
     * <code>null</code>.
     * </p>
     *
     * <pre>
     * ObjectUtils.equals(null, null)                  = true
     * ObjectUtils.equals(null, "")                    = false
     * ObjectUtils.equals("", null)                    = false
     * ObjectUtils.equals("", "")                      = true
     * ObjectUtils.equals(Boolean.TRUE, null)          = false
     * ObjectUtils.equals(Boolean.TRUE, "true")        = false
     * ObjectUtils.equals(Boolean.TRUE, Boolean.TRUE)  = true
     * ObjectUtils.equals(Boolean.TRUE, Boolean.FALSE) = false
     * </pre>
     *
     * @param object1 the first object, may be <code>null</code>
     * @param object2 the second object, may be <code>null</code>
     * @return <code>true</code> if the values of both objects are the same
     */
    public static boolean equals(Object object1, Object object2) {
      if (object1 == object2) {
        return true;
      }
      if ((object1 == null) || (object2 == null)) {
        return false;
      }
      return object1.equals(object2);
    }

    /**
     * <p>
     * Compares two objects for inequality, where either one or both objects may be
     * <code>null</code>.
     * </p>
     *
     * <pre>
     * ObjectUtils.notEqual(null, null)                  = false
     * ObjectUtils.notEqual(null, "")                    = true
     * ObjectUtils.notEqual("", null)                    = true
     * ObjectUtils.notEqual("", "")                      = false
     * ObjectUtils.notEqual(Boolean.TRUE, null)          = true
     * ObjectUtils.notEqual(Boolean.TRUE, "true")        = true
     * ObjectUtils.notEqual(Boolean.TRUE, Boolean.TRUE)  = false
     * ObjectUtils.notEqual(Boolean.TRUE, Boolean.FALSE) = true
     * </pre>
     *
     * @param object1 the first object, may be <code>null</code>
     * @param object2 the second object, may be <code>null</code>
     * @return <code>false</code> if the values of both objects are the same
     * @since 2.6
     */
    public static boolean notEqual(Object object1, Object object2) {
      return ObjectUtils.equals(object1, object2) == false;
    }

    /**
     * <p>
     * Gets the hash code of an object returning zero when the object is
     * <code>null</code>.
     * </p>
     *
     * <pre>
     * ObjectUtils.hashCode(null)   = 0
     * ObjectUtils.hashCode(obj)    = obj.hashCode()
     * </pre>
     *
     * @param obj the object to obtain the hash code of, may be <code>null</code>
     * @return the hash code of the object, or zero if null
     * @since 2.1
     */
    public static int hashCode(Object obj) {
      return (obj == null) ? 0 : obj.hashCode();
    }

    // Identity ToString
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Gets the toString that would be produced by <code>Object</code> if a class
     * did not override toString itself. <code>null</code> will return
     * <code>null</code>.
     * </p>
     *
     * <pre>
     * ObjectUtils.identityToString(null)         = null
     * ObjectUtils.identityToString("")           = "java.lang.String@1e23"
     * ObjectUtils.identityToString(Boolean.TRUE) = "java.lang.Boolean@7fa"
     * </pre>
     *
     * @param object the object to create a toString for, may be <code>null</code>
     * @return the default toString text, or <code>null</code> if <code>null</code>
     *         passed in
     */
    public static String identityToString(Object object) {
      if (object == null) {
        return null;
      }
      StringBuffer buffer = new StringBuffer();
      identityToString(buffer, object);
      return buffer.toString();
    }

    /**
     * <p>
     * Appends the toString that would be produced by <code>Object</code> if a class
     * did not override toString itself. <code>null</code> will throw a
     * NullPointerException for either of the two parameters.
     * </p>
     *
     * <pre>
     * ObjectUtils.identityToString(buf, "")            = buf.append("java.lang.String@1e23"
     * ObjectUtils.identityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa"
     * ObjectUtils.identityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa")
     * </pre>
     *
     * @param buffer the buffer to append to
     * @param object the object to create a toString for
     * @since 2.4
     */
    public static void identityToString(StringBuffer buffer, Object object) {
      if (object == null) {
        throw new NullPointerException("Cannot get the toString of a null identity");
      }
      buffer.append(object.getClass().getName()).append('@')
          .append(Integer.toHexString(System.identityHashCode(object)));
    }

    /**
     * <p>
     * Appends the toString that would be produced by <code>Object</code> if a class
     * did not override toString itself. <code>null</code> will return
     * <code>null</code>.
     * </p>
     *
     * <pre>
     * ObjectUtils.appendIdentityToString(*, null)            = null
     * ObjectUtils.appendIdentityToString(null, "")           = "java.lang.String@1e23"
     * ObjectUtils.appendIdentityToString(null, Boolean.TRUE) = "java.lang.Boolean@7fa"
     * ObjectUtils.appendIdentityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa")
     * </pre>
     *
     * @param buffer the buffer to append to, may be <code>null</code>
     * @param object the object to create a toString for, may be <code>null</code>
     * @return the default toString text, or <code>null</code> if <code>null</code>
     *         passed in
     * @since 2.0
     * @deprecated The design of this method is bad - see LANG-360. Instead, use
     *             identityToString(StringBuffer, Object).
     */
    @Deprecated
    public static StringBuffer appendIdentityToString(StringBuffer buffer, Object object) {
      if (object == null) {
        return null;
      }
      if (buffer == null) {
        buffer = new StringBuffer();
      }
      return buffer.append(object.getClass().getName()).append('@')
          .append(Integer.toHexString(System.identityHashCode(object)));
    }

    // ToString
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Gets the <code>toString</code> of an <code>Object</code> returning an empty
     * string ("") if <code>null</code> input.
     * </p>
     * 
     * <pre>
     * ObjectUtils.toString(null)         = ""
     * ObjectUtils.toString("")           = ""
     * ObjectUtils.toString("bat")        = "bat"
     * ObjectUtils.toString(Boolean.TRUE) = "true"
     * </pre>
     *
     * @param obj the Object to <code>toString</code>, may be null
     * @return the passed in Object's toString, or nullStr if <code>null</code>
     *         input
     * @see StringUtils#defaultString(String)
     * @see String#valueOf(Object)
     * @since 2.0
     */
    public static String toString(Object obj) {
      return obj == null ? "" : obj.toString();
    }

    /**
     * <p>
     * Gets the <code>toString</code> of an <code>Object</code> returning a
     * specified text if <code>null</code> input.
     * </p>
     * 
     * <pre>
     * ObjectUtils.toString(null, null)           = null
     * ObjectUtils.toString(null, "null")         = "null"
     * ObjectUtils.toString("", "null")           = ""
     * ObjectUtils.toString("bat", "null")        = "bat"
     * ObjectUtils.toString(Boolean.TRUE, "null") = "true"
     * </pre>
     *
     * @param obj     the Object to <code>toString</code>, may be null
     * @param nullStr the String to return if <code>null</code> input, may be null
     * @return the passed in Object's toString, or nullStr if <code>null</code>
     *         input
     * @see StringUtils#defaultString(String,String)
     * @see String#valueOf(Object)
     * @since 2.0
     */
    public static String toString(Object obj, String nullStr) {
      return obj == null ? nullStr : obj.toString();
    }

    // Min/Max
    // -----------------------------------------------------------------------
    /**
     * Null safe comparison of Comparables.
     *
     * @param c1 the first comparable, may be null
     * @param c2 the second comparable, may be null
     * @return the object
     *         <ul>
     *         <li>If both objects are non-null and unequal, the lesser object.
     *         <li>If both objects are non-null and equal, c1.
     *         <li>If one of the comparables is null, the non-null object.
     *         <li>If both the comparables are null, null is returned.
     *         </ul>
     */
    public static Object min(Comparable c1, Comparable c2) {
      return (compare(c1, c2, true) <= 0 ? c1 : c2);
    }

    /**
     * Null safe comparison of Comparables.
     *
     * @param c1 the first comparable, may be null
     * @param c2 the second comparable, may be null
     * @return the object
     *         <ul>
     *         <li>If both objects are non-null and unequal, the greater object.
     *         <li>If both objects are non-null and equal, c1.
     *         <li>If one of the comparables is null, the non-null object.
     *         <li>If both the comparables are null, null is returned.
     *         </ul>
     */
    public static Object max(Comparable c1, Comparable c2) {
      return (compare(c1, c2, false) >= 0 ? c1 : c2);
    }

    /**
     * Null safe comparison of Comparables. {@code null} is assumed to be less than
     * a non-{@code null} value.
     * 
     * @param c1 the first comparable, may be null
     * @param c2 the second comparable, may be null
     * @return a negative value if c1 < c2, zero if c1 = c2 and a positive value if
     *         c1 > c2
     * @since 2.6
     */
    public static int compare(Comparable c1, Comparable c2) {
      return compare(c1, c2, false);
    }

    /**
     * Null safe comparison of Comparables.
     * 
     * @param c1          the first comparable, may be null
     * @param c2          the second comparable, may be null
     * @param nullGreater if true <code>null</code> is considered greater than a
     *                    Non- <code>null</code> value or if false <code>null</code>
     *                    is considered less than a Non-<code>null</code> value
     * @return a negative value if c1 < c2, zero if c1 = c2 and a positive value if
     *         c1 > c2
     * @see java.util.Comparator#compare(Object, Object)
     * @since 2.6
     */
    public static int compare(Comparable c1, Comparable c2, boolean nullGreater) {
      if (c1 == c2) {
        return 0;
      } else if (c1 == null) {
        return (nullGreater ? 1 : -1);
      } else if (c2 == null) {
        return (nullGreater ? -1 : 1);
      }
      return c1.compareTo(c2);
    }

    // Null
    // -----------------------------------------------------------------------
    /**
     * <p>
     * Class used as a null placeholder where <code>null</code> has another meaning.
     * </p>
     *
     * <p>
     * For example, in a <code>HashMap</code> the
     * {@link java.util.HashMap#get(java.lang.Object)} method returns
     * <code>null</code> if the <code>Map</code> contains <code>null</code> or if
     * there is no matching key. The <code>Null</code> placeholder can be used to
     * distinguish between these two cases.
     * </p>
     *
     * <p>
     * Another example is <code>Hashtable</code>, where <code>null</code> cannot be
     * stored.
     * </p>
     */
    public static class Null implements Serializable {
      /**
       * Required for serialization support. Declare serialization compatibility with
       * Commons Lang 1.0
       * 
       * @see java.io.Serializable
       */
      private static final long serialVersionUID = 7092611880189329093L;

      /**
       * Restricted constructor - singleton.
       */
      Null() {
        super();
      }

      /**
       * <p>
       * Ensure singleton.
       * </p>
       * 
       * @return the singleton value
       */
      private Object readResolve() {
        return ObjectUtils.NULL;
      }
    }

  }

  /**
   * The empty String <code>""</code>.
   * 
   * @since 2.0
   */
  public static final String EMPTY = "";

  /**
   * Represents a failed index search.
   * 
   * @since 2.1
   */
  public static final int INDEX_NOT_FOUND = -1;

  /**
   * <p>
   * The maximum size to which the padding constant(s) can expand.
   * </p>
   */
  private static final int PAD_LIMIT = 8192;

  /**
   * An empty immutable <code>Object</code> array.
   */
  public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
  /**
   * An empty immutable <code>Class</code> array.
   */
  public static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
  /**
   * An empty immutable <code>String</code> array.
   */
  public static final String[] EMPTY_STRING_ARRAY = new String[0];
  /**
   * An empty immutable <code>long</code> array.
   */
  public static final long[] EMPTY_LONG_ARRAY = new long[0];
  /**
   * An empty immutable <code>Long</code> array.
   */
  public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
  /**
   * An empty immutable <code>int</code> array.
   */
  public static final int[] EMPTY_INT_ARRAY = new int[0];
  /**
   * An empty immutable <code>Integer</code> array.
   */
  public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
  /**
   * An empty immutable <code>short</code> array.
   */
  public static final short[] EMPTY_SHORT_ARRAY = new short[0];
  /**
   * An empty immutable <code>Short</code> array.
   */
  public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
  /**
   * An empty immutable <code>byte</code> array.
   */
  public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
  /**
   * An empty immutable <code>Byte</code> array.
   */
  public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
  /**
   * An empty immutable <code>double</code> array.
   */
  public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
  /**
   * An empty immutable <code>Double</code> array.
   */
  public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
  /**
   * An empty immutable <code>float</code> array.
   */
  public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
  /**
   * An empty immutable <code>Float</code> array.
   */
  public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
  /**
   * An empty immutable <code>boolean</code> array.
   */
  public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
  /**
   * An empty immutable <code>Boolean</code> array.
   */
  public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
  /**
   * An empty immutable <code>char</code> array.
   */
  public static final char[] EMPTY_CHAR_ARRAY = new char[0];
  /**
   * An empty immutable <code>Character</code> array.
   */
  public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

  /**
   * The Constructor.
   */
  private CommonSupport() {

  }

  /**
   * Throw if not china mobile.
   *
   * @param that    the that
   * @param message the message
   */
  public static void throwIfNotChinaMobile(String that, String message) {
    if (!isChinaMobile(that)) {
      throw new DataException(message);
    }
  }

  /**
   * Checks if is mobile.
   *
   * @param mobile the mobile
   * @return true, if is mobile
   */
  public static boolean isMobile(String mobile) {
    String PHONE_PATTERN = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([0,1,6,7,]))|(18[0-2,5-9])|(19[0-2,5-9]))\\d{8}$";
    boolean isPhone = Pattern.compile(PHONE_PATTERN).matcher(mobile).matches();
    return isPhone;
  }

  /**
   * Checks if is email.
   *
   * @param email the email
   * @return true, if is email
   */
  public static boolean isEmail(String email) {
    String PHONE_PATTERN = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    boolean isEmail = Pattern.compile(PHONE_PATTERN).matcher(email).matches();
    return isEmail;
  }

  /**
   * Checks if is china mobile.
   *
   * @param that the that
   * @return true, if checks if is china mobile
   */
  public static boolean isChinaMobile(String that) {
    return NumberUtils.isDigits(that) && StringUtils.length(that) == 11;
  }

  /**
   * Throw if not null.
   *
   * @param that    the that
   * @param message the message
   */
  public static void throwIfNotNull(Object that, String message) {
    if (that != null) {
      throw new DataException(message);
    }
  }

  /**
   * Throw if null.
   *
   * @param that    the that
   * @param message the message
   */
  public static void throwIfNull(Object that, String message) {
    if (that == null) {
      throw new DataException(message);
    }
  }

  /**
   * Throw if blank.
   *
   * @param that    the that
   * @param message the message
   */
  public static void throwIfBlank(String that, String message) {
    if (isBlank(that)) {
      throw new DataException(message);
    }
  }

  /**
   * Throw if blank.
   *
   * @param that    the that
   * @param message the message
   */
  public static void throwIfBlank(List<?> that, String message) {
    if (that == null || that.isEmpty()) {
      throw new DataException(message);
    }
  }

  /**
   * Throw if false.
   *
   * @param that    the that
   * @param message the message
   */
  public static void throwIfFalse(boolean that, String message) {
    if (!that) {
      throw new DataException(message);
    }
  }

  /**
   * Throw if length greater than.
   *
   * @param that    the that
   * @param length  the length
   * @param message the message
   */
  public static void throwIfLengthGreaterThan(String that, int length, String message) {
    if (StringUtils.length(that) > length) {
      throw new DataException(message);
    }
  }

  /**
   * Throw if length less than.
   *
   * @param that    the that
   * @param length  the length
   * @param message the message
   */
  public static void throwIfLengthLessThan(String that, int length, String message) {
    if (StringUtils.length(that) < length) {
      throw new DataException(message);
    }
  }

  /**
   * Throw if length not between.
   *
   * @param that       the that
   * @param lengthFrom the length from
   * @param lengthTo   the length to
   * @param message    the message
   */
  public static void throwIfLengthNotBetween(String that, int lengthFrom, int lengthTo,
      String message) {
    if (StringUtils.length(that) < lengthFrom || StringUtils.length(that) > lengthTo) {
      throw new DataException(message);
    }
  }

  /**
   * Throw that.
   *
   * @param e the e
   */
  public static void throwThat(Throwable e) {
    throw new RuntimeException(e);
  }

  /**
   * Gets the property with prefix.
   *
   * @param props        the props
   * @param prefix       the prefix
   * @param key          the key
   * @param defaultValue the default value
   * @return the property with prefix
   */
  public static String getPropertyWithPrefix(Properties props, String prefix, String key,
      String defaultValue) {
    if (isBlank(prefix)) {
      return props.getProperty(key, defaultValue);
    } else {
      return props.getProperty(prefix + "." + key, defaultValue);
    }
  }

  /**
   * Gets the property with prefix.
   *
   * @param props  the props
   * @param prefix the prefix
   * @param key    the key
   * @return the property with prefix
   */
  public static String getPropertyWithPrefix(Properties props, String prefix, String key) {
    return getPropertyWithPrefix(props, prefix, key, "");
  }

  /**
   * Read properties.
   *
   * @param is the is
   * @return the properties
   */
  public static Properties readProperties(InputStream is) {
    Properties ps = new Properties();
    try {
      if (is != null)
        ps.load(is);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      closeQuietly(is);
    }
    return ps;
  }

  /**
   * Read properties.
   *
   * @param file the file
   * @return the properties
   * @throws FileNotFoundException the file not found exception
   */
  public static Properties readProperties(File file) throws FileNotFoundException {
    return readProperties(new FileInputStream(file));
  }

  /**
   * Read properties from resource.
   *
   * @param resourceName the resource name.
   * @return the properties。如果资源不存在,返回新的Properties。
   */
  public static Properties readPropertiesFromResource(String resourceName) {
    Properties ps = new Properties();
    InputStream is = CommonSupport.class.getResourceAsStream(resourceName);
    try {
      if (is == null)
        return ps;
      ps.load(is);
    } catch (IOException e) {
      throw new RuntimeException("read resource " + resourceName + " error " + e.getMessage());
    } finally {
      closeQuietly(is);
    }
    return ps;
  }

  /**
   * Write properties.
   *
   * @param props the props
   * @param out   the out
   */
  public static void writeProperties(Properties props, OutputStream out) {
    try {
      props.store(out, null);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      closeQuietly(out);
    }
  }

  /**
   * Write properties.
   *
   * @param props the props
   * @param file  the file
   * @throws FileNotFoundException the file not found exception
   */
  public static void writeProperties(Properties props, File file) throws FileNotFoundException {
    writeProperties(props, new FileOutputStream(file));
  }

  // Empty checks
  // -----------------------------------------------------------------------
  /**
   * <p>
   * Checks if a String is empty ("") or null.
   * </p>
   *
   * <pre>
   * isEmpty(null)      = true
   * isEmpty("")        = true
   * isEmpty(" ")       = false
   * isEmpty("bob")     = false
   * isEmpty("  bob  ") = false
   * </pre>
   *
   * <p>
   * NOTE: This method changed in Lang version 2.0. It no longer trims the String.
   * That functionality is available in isBlank().
   * </p>
   *
   * @param str the String to check, may be null
   * @return <code>true</code> if the String is empty or null
   */
  public static boolean isEmpty(String str) {
    return str == null || str.length() == 0;
  }

  /**
   * <p>
   * Checks if a String is not empty ("") and not null.
   * </p>
   *
   * <pre>
   * isNotEmpty(null)      = false
   * isNotEmpty("")        = false
   * isNotEmpty(" ")       = true
   * isNotEmpty("bob")     = true
   * isNotEmpty("  bob  ") = true
   * </pre>
   *
   * @param str the String to check, may be null
   * @return <code>true</code> if the String is not empty and not null
   */
  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }

  /**
   * Checks if is not empty.
   *
   * @param any the any
   * @return true, if is not empty
   */
  public static boolean isNotEmpty(Object any) {
    if (any == null) {
      return false;
    }
    return isNotEmpty(any.toString());
  }

  /**
   * <p>
   * Checks if a String is whitespace, empty ("") or null.
   * </p>
   *
   * <pre>
   * isBlank(null)      = true
   * isBlank("")        = true
   * isBlank(" ")       = true
   * isBlank("bob")     = false
   * isBlank("  bob  ") = false
   * </pre>
   *
   * @param str the String to check, may be null
   * @return <code>true</code> if the String is null, empty or whitespace
   * @since 2.0
   */
  public static boolean isBlank(String str) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if ((Character.isWhitespace(str.charAt(i)) == false)) {
        return false;
      }
    }
    return true;
  }

  /**
   * <p>
   * Checks if a String is not empty (""), not null and not whitespace only.
   * </p>
   *
   * <pre>
   * isNotBlank(null)      = false
   * isNotBlank("")        = false
   * isNotBlank(" ")       = false
   * isNotBlank("bob")     = true
   * isNotBlank("  bob  ") = true
   * </pre>
   *
   * @param str the String to check, may be null
   * @return <code>true</code> if the String is not empty and not null and not
   *         whitespace
   * @since 2.0
   */
  public static boolean isNotBlank(String str) {
    return !isBlank(str);
  }

  /**
   * <p>
   * Checks if String contains a search String, handling <code>null</code>. This
   * method uses {@link String#indexOf(String)}.
   * </p>
   *
   * <p>
   * A <code>null</code> String will return <code>false</code>.
   * </p>
   *
   * <pre>
   * StringUtils.contains(null, *)     = false
   * StringUtils.contains(*, null)     = false
   * StringUtils.contains("", "")      = true
   * StringUtils.contains("abc", "")   = true
   * StringUtils.contains("abc", "a")  = true
   * StringUtils.contains("abc", "z")  = false
   * </pre>
   *
   * @param str       the String to check, may be null
   * @param searchStr the String to find, may be null
   * @return true if the String contains the search String, false if not or
   *         <code>null</code> string input
   * @since 2.0
   */
  public static boolean contains(String str, String searchStr) {
    if (str == null || searchStr == null) {
      return false;
    }
    return str.indexOf(searchStr) >= 0;
  }

  /**
   * <p>
   * Checks if String contains a search String irrespective of case, handling
   * <code>null</code>. Case-insensitivity is defined as by
   *
   * @param str       the String to check, may be null
   * @param searchStr the String to find, may be null
   * @return true if the String contains the search String irrespective of case or
   *         false if not or <code>null</code> string input
   *         {@link String#equalsIgnoreCase(String)}.
   * 
   *         <p>
   *         A <code>null</code> String will return <code>false</code>.
   *         </p>
   * 
   *         <pre>
   * StringUtils.contains(null, *) = false
   * StringUtils.contains(*, null) = false
   * StringUtils.contains("", "") = true
   * StringUtils.contains("abc", "") = true
   * StringUtils.contains("abc", "a") = true
   * StringUtils.contains("abc", "z") = false
   * StringUtils.contains("abc", "A") = true
   * StringUtils.contains("abc", "Z") = false
   *         </pre>
   */
  public static boolean containsIgnoreCase(String str, String searchStr) {
    if (str == null || searchStr == null) {
      return false;
    }
    int len = searchStr.length();
    int max = str.length() - len;
    for (int i = 0; i <= max; i++) {
      if (str.regionMatches(true, i, searchStr, 0, len)) {
        return true;
      }
    }
    return false;
  }

  // -----------------------------------------------------------------------
  /**
   * Unconditionally close an <code>Reader</code>.
   * <p>
   * Equivalent to {@link Reader#close()}, except any exceptions will be ignored.
   * This is typically used in finally blocks.
   *
   * @param input the Reader to close, may be null or already closed
   */
  public static void closeQuietly(Reader input) {
    try {
      if (input != null) {
        input.close();
      }
    } catch (IOException ioe) {
      // ignore
    }
  }

  /**
   * Unconditionally close a <code>Writer</code>.
   * <p>
   * Equivalent to {@link Writer#close()}, except any exceptions will be ignored.
   * This is typically used in finally blocks.
   *
   * @param output the Writer to close, may be null or already closed
   */
  public static void closeQuietly(Writer output) {
    try {
      if (output != null) {
        output.close();
      }
    } catch (IOException ioe) {
      // ignore
    }
  }

  /**
   * Unconditionally close an <code>InputStream</code>.
   * <p>
   * Equivalent to {@link InputStream#close()}, except any exceptions will be
   * ignored. This is typically used in finally blocks.
   *
   * @param input the InputStream to close, may be null or already closed
   */
  public static void closeQuietly(InputStream input) {
    try {
      if (input != null) {
        input.close();
      }
    } catch (IOException ioe) {
      // ignore
    }
  }

  /**
   * Unconditionally close an <code>OutputStream</code>.
   * <p>
   * Equivalent to {@link OutputStream#close()}, except any exceptions will be
   * ignored. This is typically used in finally blocks.
   *
   * @param output the OutputStream to close, may be null or already closed
   */
  public static void closeQuietly(OutputStream output) {
    try {
      if (output != null) {
        output.close();
      }
    } catch (IOException ioe) {
      // ignore
    }
  }

  /**
   * Close quietly.
   *
   * @param input the input
   */
  public static void closeQuietly(java.io.Closeable input) {
    try {
      if (input != null) {
        input.close();
      }
    } catch (IOException ioe) {
      // ignore
    }
  }

  /**
   * 批量关闭,简化代码.
   *
   * @param inputs the inputs
   */
  public static void closeQuietlyAll(java.io.Closeable... inputs) {
    for (java.io.Closeable input : inputs) {
      closeQuietly(input);
    }
  }

  // Substring
  // -----------------------------------------------------------------------
  /**
   * <p>
   * Gets a substring from the specified String avoiding exceptions.
   * </p>
   *
   * <p>
   * A negative start position can be used to start <code>n</code> characters from
   * the end of the String.
   * </p>
   *
   * <p>
   * A <code>null</code> String will return <code>null</code>. An empty ("")
   * String will return "".
   * </p>
   *
   * <pre>
   * StringUtils.substring(null, *)   = null
   * StringUtils.substring("", *)     = ""
   * StringUtils.substring("abc", 0)  = "abc"
   * StringUtils.substring("abc", 2)  = "c"
   * StringUtils.substring("abc", 4)  = ""
   * StringUtils.substring("abc", -2) = "bc"
   * StringUtils.substring("abc", -4) = "abc"
   * </pre>
   *
   * @param str   the String to get the substring from, may be null
   * @param start the position to start from, negative means count back from the
   *              end of the String by this many characters
   * @return substring from start position, <code>null</code> if null String input
   */
  public static String substring(String str, int start) {
    if (str == null) {
      return null;
    }

    // handle negatives, which means last n characters
    if (start < 0) {
      start = str.length() + start; // remember start is negative
    }

    if (start < 0) {
      start = 0;
    }
    if (start > str.length()) {
      return EMPTY;
    }

    return str.substring(start);
  }

  /**
   * <p>
   * Gets a substring from the specified String avoiding exceptions.
   * </p>
   *
   * <p>
   * A negative start position can be used to start/end <code>n</code> characters
   * from the end of the String.
   * </p>
   *
   * <p>
   * The returned substring starts with the character in the <code>start</code>
   * position and ends before the <code>end</code> position. All position counting
   * is zero-based -- i.e., to start at the beginning of the string use
   * <code>start = 0</code>. Negative start and end positions can be used to
   * specify offsets relative to the end of the String.
   * </p>
   *
   * <p>
   * If <code>start</code> is not strictly to the left of <code>end</code>, "" is
   * returned.
   * </p>
   *
   * <pre>
   * StringUtils.substring(null, *, *)    = null
   * StringUtils.substring("", * ,  *)    = "";
   * StringUtils.substring("abc", 0, 2)   = "ab"
   * StringUtils.substring("abc", 2, 0)   = ""
   * StringUtils.substring("abc", 2, 4)   = "c"
   * StringUtils.substring("abc", 4, 6)   = ""
   * StringUtils.substring("abc", 2, 2)   = ""
   * StringUtils.substring("abc", -2, -1) = "b"
   * StringUtils.substring("abc", -4, 2)  = "ab"
   * </pre>
   *
   * @param str   the String to get the substring from, may be null
   * @param start the position to start from, negative means count back from the
   *              end of the String by this many characters
   * @param end   the position to end at (exclusive), negative means count back
   *              from the end of the String by this many characters
   * @return substring from start position to end positon, <code>null</code> if
   *         null String input
   */
  public static String substring(String str, int start, int end) {
    if (str == null) {
      return null;
    }

    // handle negatives
    if (end < 0) {
      end = str.length() + end; // remember end is negative
    }
    if (start < 0) {
      start = str.length() + start; // remember start is negative
    }

    // check length next
    if (end > str.length()) {
      end = str.length();
    }

    // if start is greater than end, return ""
    if (start > end) {
      return EMPTY;
    }

    if (start < 0) {
      start = 0;
    }
    if (end < 0) {
      end = 0;
    }

    return str.substring(start, end);
  }

  // Left/Right/Mid
  // -----------------------------------------------------------------------
  /**
   * <p>
   * Gets the leftmost <code>len</code> characters of a String.
   * </p>
   *
   * <p>
   * If <code>len</code> characters are not available, or the String is
   * <code>null</code>, the String will be returned without an exception. An empty
   * String is returned if len is negative.
   * </p>
   *
   * <pre>
   * StringUtils.left(null, *)    = null
   * StringUtils.left(*, -ve)     = ""
   * StringUtils.left("", *)      = ""
   * StringUtils.left("abc", 0)   = ""
   * StringUtils.left("abc", 2)   = "ab"
   * StringUtils.left("abc", 4)   = "abc"
   * </pre>
   *
   * @param str the String to get the leftmost characters from, may be null
   * @param len the length of the required String
   * @return the leftmost characters, <code>null</code> if null String input
   */
  public static String left(String str, int len) {
    if (str == null) {
      return null;
    }
    if (len < 0) {
      return EMPTY;
    }
    if (str.length() <= len) {
      return str;
    }
    return str.substring(0, len);
  }

  /**
   * <p>
   * Gets the rightmost <code>len</code> characters of a String.
   * </p>
   *
   * <p>
   * If <code>len</code> characters are not available, or the String is
   * <code>null</code>, the String will be returned without an an exception. An
   * empty String is returned if len is negative.
   * </p>
   *
   * <pre>
   * StringUtils.right(null, *)    = null
   * StringUtils.right(*, -ve)     = ""
   * StringUtils.right("", *)      = ""
   * StringUtils.right("abc", 0)   = ""
   * StringUtils.right("abc", 2)   = "bc"
   * StringUtils.right("abc", 4)   = "abc"
   * </pre>
   *
   * @param str the String to get the rightmost characters from, may be null
   * @param len the length of the required String
   * @return the rightmost characters, <code>null</code> if null String input
   */
  public static String right(String str, int len) {
    if (str == null) {
      return null;
    }
    if (len < 0) {
      return EMPTY;
    }
    if (str.length() <= len) {
      return str;
    }
    return str.substring(str.length() - len);
  }

  /**
   * <p>
   * Gets <code>len</code> characters from the middle of a String.
   * </p>
   *
   * <p>
   * If <code>len</code> characters are not available, the remainder of the String
   * will be returned without an exception. If the String is <code>null</code>,
   * <code>null</code> will be returned. An empty String is returned if len is
   * negative or exceeds the length of <code>str</code>.
   * </p>
   *
   * <pre>
   * StringUtils.mid(null, *, *)    = null
   * StringUtils.mid(*, *, -ve)     = ""
   * StringUtils.mid("", 0, *)      = ""
   * StringUtils.mid("abc", 0, 2)   = "ab"
   * StringUtils.mid("abc", 0, 4)   = "abc"
   * StringUtils.mid("abc", 2, 4)   = "c"
   * StringUtils.mid("abc", 4, 2)   = ""
   * StringUtils.mid("abc", -2, 2)  = "ab"
   * </pre>
   *
   * @param str the String to get the characters from, may be null
   * @param pos the position to start from, negative treated as zero
   * @param len the length of the required String
   * @return the middle characters, <code>null</code> if null String input
   */
  public static String mid(String str, int pos, int len) {
    if (str == null) {
      return null;
    }
    if (len < 0 || pos > str.length()) {
      return EMPTY;
    }
    if (pos < 0) {
      pos = 0;
    }
    if (str.length() <= (pos + len)) {
      return str.substring(pos);
    }
    return str.substring(pos, pos + len);
  }

  // SubStringAfter/SubStringBefore
  // -----------------------------------------------------------------------
  /**
   * <p>
   * Gets the substring before the first occurrence of a separator. The separator
   * is not returned.
   * </p>
   *
   * <p>
   * A <code>null</code> string input will return <code>null</code>. An empty ("")
   * string input will return the empty string. A <code>null</code> separator will
   * return the input string.
   * </p>
   *
   * <p>
   * If nothing is found, the string input is returned.
   * </p>
   *
   * <pre>
   * StringUtils.substringBefore(null, *)      = null
   * StringUtils.substringBefore("", *)        = ""
   * StringUtils.substringBefore("abc", "a")   = ""
   * StringUtils.substringBefore("abcba", "b") = "a"
   * StringUtils.substringBefore("abc", "c")   = "ab"
   * StringUtils.substringBefore("abc", "d")   = "abc"
   * StringUtils.substringBefore("abc", "")    = ""
   * StringUtils.substringBefore("abc", null)  = "abc"
   * </pre>
   *
   * @param str       the String to get a substring from, may be null
   * @param separator the String to search for, may be null
   * @return the substring before the first occurrence of the separator,
   *         <code>null</code> if null String input
   * @since 2.0
   */
  public static String substringBefore(String str, String separator) {
    if (isEmpty(str) || separator == null) {
      return str;
    }
    if (separator.length() == 0) {
      return EMPTY;
    }
    int pos = str.indexOf(separator);
    if (pos == INDEX_NOT_FOUND) {
      return str;
    }
    return str.substring(0, pos);
  }

  /**
   * <p>
   * Gets the substring after the first occurrence of a separator. The separator
   * is not returned.
   * </p>
   *
   * <p>
   * A <code>null</code> string input will return <code>null</code>. An empty ("")
   * string input will return the empty string. A <code>null</code> separator will
   * return the empty string if the input string is not <code>null</code>.
   * </p>
   *
   * <p>
   * If nothing is found, the empty string is returned.
   * </p>
   *
   * <pre>
   * StringUtils.substringAfter(null, *)      = null
   * StringUtils.substringAfter("", *)        = ""
   * StringUtils.substringAfter(*, null)      = ""
   * StringUtils.substringAfter("abc", "a")   = "bc"
   * StringUtils.substringAfter("abcba", "b") = "cba"
   * StringUtils.substringAfter("abc", "c")   = ""
   * StringUtils.substringAfter("abc", "d")   = ""
   * StringUtils.substringAfter("abc", "")    = "abc"
   * </pre>
   *
   * @param str       the String to get a substring from, may be null
   * @param separator the String to search for, may be null
   * @return the substring after the first occurrence of the separator,
   *         <code>null</code> if null String input
   * @since 2.0
   */
  public static String substringAfter(String str, String separator) {
    if (isEmpty(str)) {
      return str;
    }
    if (separator == null) {
      return EMPTY;
    }
    int pos = str.indexOf(separator);
    if (pos == INDEX_NOT_FOUND) {
      return EMPTY;
    }
    return str.substring(pos + separator.length());
  }

  /**
   * <p>
   * Gets the substring before the last occurrence of a separator. The separator
   * is not returned.
   * </p>
   *
   * <p>
   * A <code>null</code> string input will return <code>null</code>. An empty ("")
   * string input will return the empty string. An empty or <code>null</code>
   * separator will return the input string.
   * </p>
   *
   * <p>
   * If nothing is found, the string input is returned.
   * </p>
   *
   * <pre>
   * StringUtils.substringBeforeLast(null, *)      = null
   * StringUtils.substringBeforeLast("", *)        = ""
   * StringUtils.substringBeforeLast("abcba", "b") = "abc"
   * StringUtils.substringBeforeLast("abc", "c")   = "ab"
   * StringUtils.substringBeforeLast("a", "a")     = ""
   * StringUtils.substringBeforeLast("a", "z")     = "a"
   * StringUtils.substringBeforeLast("a", null)    = "a"
   * StringUtils.substringBeforeLast("a", "")      = "a"
   * </pre>
   *
   * @param str       the String to get a substring from, may be null
   * @param separator the String to search for, may be null
   * @return the substring before the last occurrence of the separator,
   *         <code>null</code> if null String input
   * @since 2.0
   */
  public static String substringBeforeLast(String str, String separator) {
    if (isEmpty(str) || isEmpty(separator)) {
      return str;
    }
    int pos = str.lastIndexOf(separator);
    if (pos == INDEX_NOT_FOUND) {
      return str;
    }
    return str.substring(0, pos);
  }

  /**
   * <p>
   * Gets the substring after the last occurrence of a separator. The separator is
   * not returned.
   * </p>
   *
   * <p>
   * A <code>null</code> string input will return <code>null</code>. An empty ("")
   * string input will return the empty string. An empty or <code>null</code>
   * separator will return the empty string if the input string is not
   * <code>null</code>.
   * </p>
   *
   * <p>
   * If nothing is found, the empty string is returned.
   * </p>
   *
   * <pre>
   * StringUtils.substringAfterLast(null, *)      = null
   * StringUtils.substringAfterLast("", *)        = ""
   * StringUtils.substringAfterLast(*, "")        = ""
   * StringUtils.substringAfterLast(*, null)      = ""
   * StringUtils.substringAfterLast("abc", "a")   = "bc"
   * StringUtils.substringAfterLast("abcba", "b") = "a"
   * StringUtils.substringAfterLast("abc", "c")   = ""
   * StringUtils.substringAfterLast("a", "a")     = ""
   * StringUtils.substringAfterLast("a", "z")     = ""
   * </pre>
   *
   * @param str       the String to get a substring from, may be null
   * @param separator the String to search for, may be null
   * @return the substring after the last occurrence of the separator,
   *         <code>null</code> if null String input
   * @since 2.0
   */
  public static String substringAfterLast(String str, String separator) {
    if (isEmpty(str)) {
      return str;
    }
    if (isEmpty(separator)) {
      return EMPTY;
    }
    int pos = str.lastIndexOf(separator);
    if (pos == INDEX_NOT_FOUND || pos == (str.length() - separator.length())) {
      return EMPTY;
    }
    return str.substring(pos + separator.length());
  }

  // Substring between
  // -----------------------------------------------------------------------
  /**
   * <p>
   * Gets the String that is nested in between two instances of the same String.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>. A
   * <code>null</code> tag returns <code>null</code>.
   * </p>
   *
   * <pre>
   * StringUtils.substringBetween(null, *)            = null
   * StringUtils.substringBetween("", "")             = ""
   * StringUtils.substringBetween("", "tag")          = null
   * StringUtils.substringBetween("tagabctag", null)  = null
   * StringUtils.substringBetween("tagabctag", "")    = ""
   * StringUtils.substringBetween("tagabctag", "tag") = "abc"
   * </pre>
   *
   * @param str the String containing the substring, may be null
   * @param tag the String before and after the substring, may be null
   * @return the substring, <code>null</code> if no match
   * @since 2.0
   */
  public static String substringBetween(String str, String tag) {
    return substringBetween(str, tag, tag);
  }

  /**
   * <p>
   * Gets the String that is nested in between two Strings. Only the first match
   * is returned.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>. A
   * <code>null</code> open/close returns <code>null</code> (no match). An empty
   * ("") open and close returns an empty string.
   * </p>
   *
   * <pre>
   * StringUtils.substringBetween("wx[b]yz", "[", "]") = "b"
   * StringUtils.substringBetween(null, *, *)          = null
   * StringUtils.substringBetween(*, null, *)          = null
   * StringUtils.substringBetween(*, *, null)          = null
   * StringUtils.substringBetween("", "", "")          = ""
   * StringUtils.substringBetween("", "", "]")         = null
   * StringUtils.substringBetween("", "[", "]")        = null
   * StringUtils.substringBetween("yabcz", "", "")     = ""
   * StringUtils.substringBetween("yabcz", "y", "z")   = "abc"
   * StringUtils.substringBetween("yabczyabcz", "y", "z")   = "abc"
   * </pre>
   *
   * @param str   the String containing the substring, may be null
   * @param open  the String before the substring, may be null
   * @param close the String after the substring, may be null
   * @return the substring, <code>null</code> if no match
   * @since 2.0
   */
  public static String substringBetween(String str, String open, String close) {
    if (str == null || open == null || close == null) {
      return null;
    }
    int start = str.indexOf(open);
    if (start != INDEX_NOT_FOUND) {
      int end = str.indexOf(close, start + open.length());
      if (end != INDEX_NOT_FOUND) {
        return str.substring(start + open.length(), end);
      }
    }
    return null;
  }

  /**
   * <p>
   * Searches a String for substrings delimited by a start and end tag, returning
   * all matching substrings in an array.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>. A
   * <code>null</code> open/close returns <code>null</code> (no match). An empty
   * ("") open/close returns <code>null</code> (no match).
   * </p>
   *
   * <pre>
   * StringUtils.substringsBetween("[a][b][c]", "[", "]") = ["a","b","c"]
   * StringUtils.substringsBetween(null, *, *)            = null
   * StringUtils.substringsBetween(*, null, *)            = null
   * StringUtils.substringsBetween(*, *, null)            = null
   * StringUtils.substringsBetween("", "[", "]")          = []
   * </pre>
   *
   * @param str   the String containing the substrings, null returns null, empty
   *              returns empty
   * @param open  the String identifying the start of the substring, empty returns
   *              null
   * @param close the String identifying the end of the substring, empty returns
   *              null
   * @return a String Array of substrings, or <code>null</code> if no match
   * @since 2.3
   */
  public static String[] substringsBetween(String str, String open, String close) {
    if (str == null || isEmpty(open) || isEmpty(close)) {
      return null;
    }
    int strLen = str.length();
    if (strLen == 0) {
      return EMPTY_STRING_ARRAY;
    }
    int closeLen = close.length();
    int openLen = open.length();
    List list = new ArrayList();
    int pos = 0;
    while (pos < (strLen - closeLen)) {
      int start = str.indexOf(open, pos);
      if (start < 0) {
        break;
      }
      start += openLen;
      int end = str.indexOf(close, start);
      if (end < 0) {
        break;
      }
      list.add(str.substring(start, end));
      pos = end + closeLen;
    }
    if (list.isEmpty()) {
      return null;
    }
    return (String[]) list.toArray(new String[list.size()]);
  }

  // Equals
  // -----------------------------------------------------------------------
  /**
   * <p>
   * Compares two Strings, returning <code>true</code> if they are equal.
   * </p>
   * 
   * <p>
   * <code>null</code>s are handled without exceptions. Two <code>null</code>
   * references are considered to be equal. The comparison is case sensitive.
   * </p>
   * 
   * <pre>
   * StringUtils.equals(null, null)   = true
   * StringUtils.equals(null, "abc")  = false
   * StringUtils.equals("abc", null)  = false
   * StringUtils.equals("abc", "abc") = true
   * StringUtils.equals("abc", "ABC") = false
   * </pre>
   *
   * @param str1 the first String, may be null
   * @param str2 the second String, may be null
   * @return <code>true</code> if the Strings are equal, case sensitive, or both
   *         <code>null</code>
   * @see java.lang.String#equals(Object)
   */
  public static boolean equals(String str1, String str2) {
    return str1 == null ? str2 == null : str1.equals(str2);
  }

  /**
   * <p>
   * Compares two Strings, returning <code>true</code> if they are equal ignoring
   * the case.
   * </p>
   * 
   * <p>
   * <code>null</code>s are handled without exceptions. Two <code>null</code>
   * references are considered equal. Comparison is case insensitive.
   * </p>
   * 
   * <pre>
   * StringUtils.equalsIgnoreCase(null, null)   = true
   * StringUtils.equalsIgnoreCase(null, "abc")  = false
   * StringUtils.equalsIgnoreCase("abc", null)  = false
   * StringUtils.equalsIgnoreCase("abc", "abc") = true
   * StringUtils.equalsIgnoreCase("abc", "ABC") = true
   * </pre>
   *
   * @param str1 the first String, may be null
   * @param str2 the second String, may be null
   * @return <code>true</code> if the Strings are equal, case insensitive, or both
   *         <code>null</code>
   * @see java.lang.String#equalsIgnoreCase(String)
   */
  public static boolean equalsIgnoreCase(String str1, String str2) {
    return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
  }

  /**
   * <p>
   * Joins the elements of the provided array into a single String containing the
   * provided list of elements.
   * </p>
   *
   * <p>
   * No delimiter is added before or after the list. A <code>null</code> separator
   * is the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * </p>
   *
   * <pre>
   * StringUtils.join(null, *)                = null
   * StringUtils.join([], *)                  = ""
   * StringUtils.join([null], *)              = ""
   * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
   * StringUtils.join(["a", "b", "c"], null)  = "abc"
   * StringUtils.join(["a", "b", "c"], "")    = "abc"
   * StringUtils.join([null, "", "a"], ',')   = ",,a"
   * </pre>
   *
   * @param array     the array of values to join together, may be null
   * @param separator the separator character to use, null treated as ""
   * @return the joined String, <code>null</code> if null array input
   */
  public static String join(Object[] array, String separator) {
    if (array == null) {
      return null;
    }
    return join(array, separator, 0, array.length);
  }

  /**
   * <p>
   * Joins the elements of the provided array into a single String containing the
   * provided list of elements.
   * </p>
   *
   * <p>
   * No delimiter is added before or after the list. A <code>null</code> separator
   * is the same as an empty String (""). Null objects or empty strings within the
   * array are represented by empty strings.
   * </p>
   *
   * <pre>
   * StringUtils.join(null, *)                = null
   * StringUtils.join([], *)                  = ""
   * StringUtils.join([null], *)              = ""
   * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
   * StringUtils.join(["a", "b", "c"], null)  = "abc"
   * StringUtils.join(["a", "b", "c"], "")    = "abc"
   * StringUtils.join([null, "", "a"], ',')   = ",,a"
   * </pre>
   *
   * @param array      the array of values to join together, may be null
   * @param separator  the separator character to use, null treated as ""
   * @param startIndex the first index to start joining from. It is an error to
   *                   pass in an end index past the end of the array
   * @param endIndex   the index to stop joining from (exclusive). It is an error
   *                   to pass in an end index past the end of the array
   * @return the joined String, <code>null</code> if null array input
   */
  public static String join(Object[] array, String separator, int startIndex, int endIndex) {
    if (array == null) {
      return null;
    }
    if (separator == null) {
      separator = EMPTY;
    }

    // endIndex - startIndex > 0: Len = NofStrings *(len(firstString) +
    // len(separator))
    // (Assuming that all Strings are roughly equally long)
    int bufSize = (endIndex - startIndex);
    if (bufSize <= 0) {
      return EMPTY;
    }

    bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length())
        + separator.length());

    StringBuffer buf = new StringBuffer(bufSize);
    for (int i = startIndex; i < endIndex; i++) {
      if (i > startIndex) {
        buf.append(separator);
      }
      if (array[i] != null) {
        buf.append(array[i]);
      }
    }
    return buf.toString();
  }

  /**
   * <p>
   * Joins the elements of the provided <code>Iterator</code> into a single String
   * containing the provided elements.
   * </p>
   *
   * <p>
   * No delimiter is added before or after the list. Null objects or empty strings
   * within the iteration are represented by empty strings.
   * </p>
   *
   * <p>
   * See the examples here: {@link #join(Object[],char)}.
   * </p>
   *
   * @param iterator  the <code>Iterator</code> of values to join together, may be
   *                  null
   * @param separator the separator character to use
   * @return the joined String, <code>null</code> if null iterator input
   * @since 2.0
   */
  public static String join(Iterator iterator, char separator) {

    // handle null, zero and one elements before building a buffer
    if (iterator == null) {
      return null;
    }
    if (!iterator.hasNext()) {
      return EMPTY;
    }
    Object first = iterator.next();
    if (!iterator.hasNext()) {
      return ObjectUtils.toString(first);
    }

    // two or more elements
    StringBuffer buf = new StringBuffer(256); // Java default is 16, probably too
    // small
    if (first != null) {
      buf.append(first);
    }

    while (iterator.hasNext()) {
      buf.append(separator);
      Object obj = iterator.next();
      if (obj != null) {
        buf.append(obj);
      }
    }

    return buf.toString();
  }

  /**
   * <p>
   * Joins the elements of the provided <code>Iterator</code> into a single String
   * containing the provided elements.
   * </p>
   *
   * <p>
   * No delimiter is added before or after the list. A <code>null</code> separator
   * is the same as an empty String ("").
   * </p>
   *
   * <p>
   * See the examples here: {@link #join(Object[],String)}.
   * </p>
   *
   * @param iterator  the <code>Iterator</code> of values to join together, may be
   *                  null
   * @param separator the separator character to use, null treated as ""
   * @return the joined String, <code>null</code> if null iterator input
   */
  public static String join(Iterator iterator, String separator) {

    // handle null, zero and one elements before building a buffer
    if (iterator == null) {
      return null;
    }
    if (!iterator.hasNext()) {
      return EMPTY;
    }
    Object first = iterator.next();
    if (!iterator.hasNext()) {
      return ObjectUtils.toString(first);
    }

    // two or more elements
    StringBuffer buf = new StringBuffer(256); // Java default is 16, probably too
    // small
    if (first != null) {
      buf.append(first);
    }

    while (iterator.hasNext()) {
      if (separator != null) {
        buf.append(separator);
      }
      Object obj = iterator.next();
      if (obj != null) {
        buf.append(obj);
      }
    }
    return buf.toString();
  }

  /**
   * <p>
   * Joins the elements of the provided <code>Collection</code> into a single
   * String containing the provided elements.
   * </p>
   *
   * <p>
   * No delimiter is added before or after the list. Null objects or empty strings
   * within the iteration are represented by empty strings.
   * </p>
   *
   * <p>
   * See the examples here: {@link #join(Object[],char)}.
   * </p>
   *
   * @param collection the <code>Collection</code> of values to join together, may
   *                   be null
   * @param separator  the separator character to use
   * @return the joined String, <code>null</code> if null iterator input
   * @since 2.3
   */
  public static String join(Collection collection, char separator) {
    if (collection == null) {
      return null;
    }
    return join(collection.iterator(), separator);
  }

  /**
   * <p>
   * Joins the elements of the provided <code>Collection</code> into a single
   * String containing the provided elements.
   * </p>
   *
   * <p>
   * No delimiter is added before or after the list. A <code>null</code> separator
   * is the same as an empty String ("").
   * </p>
   *
   * <p>
   * See the examples here: {@link #join(Object[],String)}.
   * </p>
   *
   * @param collection the <code>Collection</code> of values to join together, may
   *                   be null
   * @param separator  the separator character to use, null treated as ""
   * @return the joined String, <code>null</code> if null iterator input
   * @since 2.3
   */
  public static String join(Collection collection, String separator) {
    if (collection == null) {
      return null;
    }
    return join(collection.iterator(), separator);
  }

  /**
   * <p>
   * Capitalizes a String changing the first letter to title case as per.
   *
   * @param str the String to capitalize, may be null
   * @return the capitalized String, <code>null</code> if null String input
   *         {@link Character#toTitleCase(char)}. No other letters are changed.
   *         </p>
   * 
   *         <p>
   *         For a word based algorithm, see {#capitalize(String)}. A
   *         <code>null</code> input String returns <code>null</code>.
   *         </p>
   * 
   *         <pre>
   * StringUtils.capitalize(null)  = null
   * StringUtils.capitalize("")    = ""
   * StringUtils.capitalize("cat") = "Cat"
   * StringUtils.capitalize("cAt") = "CAt"
   *         </pre>
   * 
   * @since 2.0
   */
  public static String capitalize(String str) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0) {
      return str;
    }
    return new StringBuilder(strLen).append(Character.toTitleCase(str.charAt(0)))
        .append(str.substring(1)).toString();
  }

  /**
   * <p>
   * Uncapitalizes a String changing the first letter to title case as per.
   *
   * @param str the String to uncapitalize, may be null
   * @return the uncapitalized String, <code>null</code> if null String input
   *         {@link Character#toLowerCase(char)}. No other letters are changed.
   *         </p>
   * 
   *         <p>
   *         For a word based algorithm, see {#uncapitalize(String)}. A
   *         <code>null</code> input String returns <code>null</code>.
   *         </p>
   * 
   *         <pre>
   * StringUtils.uncapitalize(null)  = null
   * StringUtils.uncapitalize("")    = ""
   * StringUtils.uncapitalize("Cat") = "cat"
   * StringUtils.uncapitalize("CAT") = "cAT"
   *         </pre>
   * 
   * @since 2.0
   */
  public static String uncapitalize(String str) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0) {
      return str;
    }
    return new StringBuilder(strLen).append(Character.toLowerCase(str.charAt(0)))
        .append(str.substring(1)).toString();
  }

  // Splitting
  // -----------------------------------------------------------------------
  /**
   * <p>
   * Splits the provided text into an array, using whitespace as the separator.
   * Whitespace is defined by {@link Character#isWhitespace(char)}.
   * </p>
   *
   * <p>
   * The separator is not included in the returned String array. Adjacent
   * separators are treated as one separator. For more control over the split use
   * the StrTokenizer class.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>.
   * </p>
   *
   * <pre>
   * StringUtils.split(null)       = null
   * StringUtils.split("")         = []
   * StringUtils.split("abc def")  = ["abc", "def"]
   * StringUtils.split("abc  def") = ["abc", "def"]
   * StringUtils.split(" abc ")    = ["abc"]
   * </pre>
   *
   * @param str the String to parse, may be null
   * @return an array of parsed Strings, <code>null</code> if null String input
   */
  public static String[] split(String str) {
    return split(str, null, -1);
  }

  /**
   * <p>
   * Splits the provided text into an array, separator specified. This is an
   * alternative to using StringTokenizer.
   * </p>
   *
   * <p>
   * The separator is not included in the returned String array. Adjacent
   * separators are treated as one separator. For more control over the split use
   * the StrTokenizer class.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>.
   * </p>
   *
   * <pre>
   * StringUtils.split(null, *)         = null
   * StringUtils.split("", *)           = []
   * StringUtils.split("a.b.c", '.')    = ["a", "b", "c"]
   * StringUtils.split("a..b.c", '.')   = ["a", "b", "c"]
   * StringUtils.split("a:b:c", '.')    = ["a:b:c"]
   * StringUtils.split("a b c", ' ')    = ["a", "b", "c"]
   * </pre>
   *
   * @param str           the String to parse, may be null
   * @param separatorChar the character used as the delimiter
   * @return an array of parsed Strings, <code>null</code> if null String input
   * @since 2.0
   */
  public static String[] split(String str, char separatorChar) {
    return splitWorker(str, separatorChar, false);
  }

  /**
   * <p>
   * Splits the provided text into an array, separators specified. This is an
   * alternative to using StringTokenizer.
   * </p>
   *
   * <p>
   * The separator is not included in the returned String array. Adjacent
   * separators are treated as one separator. For more control over the split use
   * the StrTokenizer class.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>. A
   * <code>null</code> separatorChars splits on whitespace.
   * </p>
   *
   * <pre>
   * StringUtils.split(null, *)         = null
   * StringUtils.split("", *)           = []
   * StringUtils.split("abc def", null) = ["abc", "def"]
   * StringUtils.split("abc def", " ")  = ["abc", "def"]
   * StringUtils.split("abc  def", " ") = ["abc", "def"]
   * StringUtils.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
   * </pre>
   *
   * @param str            the String to parse, may be null
   * @param separatorChars the characters used as the delimiters,
   *                       <code>null</code> splits on whitespace
   * @return an array of parsed Strings, <code>null</code> if null String input
   */
  public static String[] split(String str, String separatorChars) {
    return splitWorker(str, separatorChars, -1, false);
  }

  /**
   * <p>
   * Splits the provided text into an array with a maximum length, separators
   * specified.
   * </p>
   *
   * <p>
   * The separator is not included in the returned String array. Adjacent
   * separators are treated as one separator.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>. A
   * <code>null</code> separatorChars splits on whitespace.
   * </p>
   *
   * <p>
   * If more than <code>max</code> delimited substrings are found, the last
   * returned string includes all characters after the first <code>max - 1</code>
   * returned strings (including separator characters).
   * </p>
   *
   * <pre>
   * StringUtils.split(null, *, *)            = null
   * StringUtils.split("", *, *)              = []
   * StringUtils.split("ab de fg", null, 0)   = ["ab", "cd", "ef"]
   * StringUtils.split("ab   de fg", null, 0) = ["ab", "cd", "ef"]
   * StringUtils.split("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
   * StringUtils.split("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
   * </pre>
   *
   * @param str            the String to parse, may be null
   * @param separatorChars the characters used as the delimiters,
   *                       <code>null</code> splits on whitespace
   * @param max            the maximum number of elements to include in the array.
   *                       A zero or negative value implies no limit
   * @return an array of parsed Strings, <code>null</code> if null String input
   */
  public static String[] split(String str, String separatorChars, int max) {
    return splitWorker(str, separatorChars, max, false);
  }

  /**
   * <p>
   * Splits the provided text into an array, separator string specified.
   * </p>
   *
   * <p>
   * The separator(s) will not be included in the returned String array. Adjacent
   * separators are treated as one separator.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>. A
   * <code>null</code> separator splits on whitespace.
   * </p>
   *
   * <pre>
   * StringUtils.splitByWholeSeparator(null, *)               = null
   * StringUtils.splitByWholeSeparator("", *)                 = []
   * StringUtils.splitByWholeSeparator("ab de fg", null)      = ["ab", "de", "fg"]
   * StringUtils.splitByWholeSeparator("ab   de fg", null)    = ["ab", "de", "fg"]
   * StringUtils.splitByWholeSeparator("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
   * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
   * </pre>
   *
   * @param str       the String to parse, may be null
   * @param separator String containing the String to be used as a delimiter,
   *                  <code>null</code> splits on whitespace
   * @return an array of parsed Strings, <code>null</code> if null String was
   *         input
   */
  public static String[] splitByWholeSeparator(String str, String separator) {
    return splitByWholeSeparatorWorker(str, separator, -1, false);
  }

  /**
   * <p>
   * Splits the provided text into an array, separator string specified. Returns a
   * maximum of <code>max</code> substrings.
   * </p>
   *
   * <p>
   * The separator(s) will not be included in the returned String array. Adjacent
   * separators are treated as one separator.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>. A
   * <code>null</code> separator splits on whitespace.
   * </p>
   *
   * <pre>
   * StringUtils.splitByWholeSeparator(null, *, *)               = null
   * StringUtils.splitByWholeSeparator("", *, *)                 = []
   * StringUtils.splitByWholeSeparator("ab de fg", null, 0)      = ["ab", "de", "fg"]
   * StringUtils.splitByWholeSeparator("ab   de fg", null, 0)    = ["ab", "de", "fg"]
   * StringUtils.splitByWholeSeparator("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
   * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]
   * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]
   * </pre>
   *
   * @param str       the String to parse, may be null
   * @param separator String containing the String to be used as a delimiter,
   *                  <code>null</code> splits on whitespace
   * @param max       the maximum number of elements to include in the returned
   *                  array. A zero or negative value implies no limit.
   * @return an array of parsed Strings, <code>null</code> if null String was
   *         input
   */
  public static String[] splitByWholeSeparator(String str, String separator, int max) {
    return splitByWholeSeparatorWorker(str, separator, max, false);
  }

  /**
   * <p>
   * Splits the provided text into an array, separator string specified.
   * </p>
   *
   * <p>
   * The separator is not included in the returned String array. Adjacent
   * separators are treated as separators for empty tokens. For more control over
   * the split use the StrTokenizer class.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>. A
   * <code>null</code> separator splits on whitespace.
   * </p>
   *
   * <pre>
   * StringUtils.splitByWholeSeparatorPreserveAllTokens(null, *)               = null
   * StringUtils.splitByWholeSeparatorPreserveAllTokens("", *)                 = []
   * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab de fg", null)      = ["ab", "de", "fg"]
   * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab   de fg", null)    = ["ab", "", "", "de", "fg"]
   * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
   * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
   * </pre>
   *
   * @param str       the String to parse, may be null
   * @param separator String containing the String to be used as a delimiter,
   *                  <code>null</code> splits on whitespace
   * @return an array of parsed Strings, <code>null</code> if null String was
   *         input
   * @since 2.4
   */
  public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator) {
    return splitByWholeSeparatorWorker(str, separator, -1, true);
  }

  /**
   * <p>
   * Splits the provided text into an array, separator string specified. Returns a
   * maximum of <code>max</code> substrings.
   * </p>
   *
   * <p>
   * The separator is not included in the returned String array. Adjacent
   * separators are treated as separators for empty tokens. For more control over
   * the split use the StrTokenizer class.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>. A
   * <code>null</code> separator splits on whitespace.
   * </p>
   *
   * <pre>
   * StringUtils.splitByWholeSeparatorPreserveAllTokens(null, *, *)               = null
   * StringUtils.splitByWholeSeparatorPreserveAllTokens("", *, *)                 = []
   * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab de fg", null, 0)      = ["ab", "de", "fg"]
   * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab   de fg", null, 0)    = ["ab", "", "", "de", "fg"]
   * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
   * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]
   * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]
   * </pre>
   *
   * @param str       the String to parse, may be null
   * @param separator String containing the String to be used as a delimiter,
   *                  <code>null</code> splits on whitespace
   * @param max       the maximum number of elements to include in the returned
   *                  array. A zero or negative value implies no limit.
   * @return an array of parsed Strings, <code>null</code> if null String was
   *         input
   * @since 2.4
   */
  public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator,
      int max) {
    return splitByWholeSeparatorWorker(str, separator, max, true);
  }

  /**
   * Performs the logic for the
   * <code>splitByWholeSeparatorPreserveAllTokens</code> methods.
   *
   * @param str               the String to parse, may be <code>null</code>
   * @param separator         String containing the String to be used as a
   *                          delimiter, <code>null</code> splits on whitespace
   * @param max               the maximum number of elements to include in the
   *                          returned array. A zero or negative value implies no
   *                          limit.
   * @param preserveAllTokens if <code>true</code>, adjacent separators are
   *                          treated as empty token separators; if
   *                          <code>false</code>, adjacent separators are treated
   *                          as one separator.
   * @return an array of parsed Strings, <code>null</code> if null String input
   * @since 2.4
   */
  private static String[] splitByWholeSeparatorWorker(String str, String separator, int max,
      boolean preserveAllTokens) {
    if (str == null) {
      return null;
    }

    int len = str.length();

    if (len == 0) {
      return EMPTY_STRING_ARRAY;
    }

    if ((separator == null) || (EMPTY.equals(separator))) {
      // Split on whitespace.
      return splitWorker(str, null, max, preserveAllTokens);
    }

    int separatorLength = separator.length();

    ArrayList substrings = new ArrayList();
    int numberOfSubstrings = 0;
    int beg = 0;
    int end = 0;
    while (end < len) {
      end = str.indexOf(separator, beg);

      if (end > -1) {
        if (end > beg) {
          numberOfSubstrings += 1;

          if (numberOfSubstrings == max) {
            end = len;
            substrings.add(str.substring(beg));
          } else {
            // The following is OK, because String.substring( beg, end ) excludes
            // the character at the position 'end'.
            substrings.add(str.substring(beg, end));

            // Set the starting point for the next search.
            // The following is equivalent to beg = end + (separatorLength - 1) + 1,
            // which is the right calculation:
            beg = end + separatorLength;
          }
        } else {
          // We found a consecutive occurrence of the separator, so skip it.
          if (preserveAllTokens) {
            numberOfSubstrings += 1;
            if (numberOfSubstrings == max) {
              end = len;
              substrings.add(str.substring(beg));
            } else {
              substrings.add(EMPTY);
            }
          }
          beg = end + separatorLength;
        }
      } else {
        // String.substring( beg ) goes from 'beg' to the end of the String.
        substrings.add(str.substring(beg));
        end = len;
      }
    }

    return (String[]) substrings.toArray(new String[substrings.size()]);
  }

  // -----------------------------------------------------------------------
  /**
   * <p>
   * Splits the provided text into an array, using whitespace as the separator,
   * preserving all tokens, including empty tokens created by adjacent separators.
   * This is an alternative to using StringTokenizer. Whitespace is defined by
   * {@link Character#isWhitespace(char)}.
   * </p>
   *
   * <p>
   * The separator is not included in the returned String array. Adjacent
   * separators are treated as separators for empty tokens. For more control over
   * the split use the StrTokenizer class.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>.
   * </p>
   *
   * <pre>
   * StringUtils.splitPreserveAllTokens(null)       = null
   * StringUtils.splitPreserveAllTokens("")         = []
   * StringUtils.splitPreserveAllTokens("abc def")  = ["abc", "def"]
   * StringUtils.splitPreserveAllTokens("abc  def") = ["abc", "", "def"]
   * StringUtils.splitPreserveAllTokens(" abc ")    = ["", "abc", ""]
   * </pre>
   *
   * @param str the String to parse, may be <code>null</code>
   * @return an array of parsed Strings, <code>null</code> if null String input
   * @since 2.1
   */
  public static String[] splitPreserveAllTokens(String str) {
    return splitWorker(str, null, -1, true);
  }

  /**
   * <p>
   * Splits the provided text into an array, separator specified, preserving all
   * tokens, including empty tokens created by adjacent separators. This is an
   * alternative to using StringTokenizer.
   * </p>
   *
   * <p>
   * The separator is not included in the returned String array. Adjacent
   * separators are treated as separators for empty tokens. For more control over
   * the split use the StrTokenizer class.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>.
   * </p>
   *
   * <pre>
   * StringUtils.splitPreserveAllTokens(null, *)         = null
   * StringUtils.splitPreserveAllTokens("", *)           = []
   * StringUtils.splitPreserveAllTokens("a.b.c", '.')    = ["a", "b", "c"]
   * StringUtils.splitPreserveAllTokens("a..b.c", '.')   = ["a", "", "b", "c"]
   * StringUtils.splitPreserveAllTokens("a:b:c", '.')    = ["a:b:c"]
   * StringUtils.splitPreserveAllTokens("a\tb\nc", null) = ["a", "b", "c"]
   * StringUtils.splitPreserveAllTokens("a b c", ' ')    = ["a", "b", "c"]
   * StringUtils.splitPreserveAllTokens("a b c ", ' ')   = ["a", "b", "c", ""]
   * StringUtils.splitPreserveAllTokens("a b c  ", ' ')   = ["a", "b", "c", "", ""]
   * StringUtils.splitPreserveAllTokens(" a b c", ' ')   = ["", a", "b", "c"]
   * StringUtils.splitPreserveAllTokens("  a b c", ' ')  = ["", "", a", "b", "c"]
   * StringUtils.splitPreserveAllTokens(" a b c ", ' ')  = ["", a", "b", "c", ""]
   * </pre>
   *
   * @param str           the String to parse, may be <code>null</code>
   * @param separatorChar the character used as the delimiter, <code>null</code>
   *                      splits on whitespace
   * @return an array of parsed Strings, <code>null</code> if null String input
   * @since 2.1
   */
  public static String[] splitPreserveAllTokens(String str, char separatorChar) {
    return splitWorker(str, separatorChar, true);
  }

  /**
   * Performs the logic for the <code>split</code> and
   * <code>splitPreserveAllTokens</code> methods that do not return a maximum
   * array length.
   *
   * @param str               the String to parse, may be <code>null</code>
   * @param separatorChar     the separate character
   * @param preserveAllTokens if <code>true</code>, adjacent separators are
   *                          treated as empty token separators; if
   *                          <code>false</code>, adjacent separators are treated
   *                          as one separator.
   * @return an array of parsed Strings, <code>null</code> if null String input
   */
  private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens) {
    // Performance tuned for 2.0 (JDK1.4)

    if (str == null) {
      return null;
    }
    int len = str.length();
    if (len == 0) {
      return EMPTY_STRING_ARRAY;
    }
    List list = new ArrayList();
    int i = 0, start = 0;
    boolean match = false;
    boolean lastMatch = false;
    while (i < len) {
      if (str.charAt(i) == separatorChar) {
        if (match || preserveAllTokens) {
          list.add(str.substring(start, i));
          match = false;
          lastMatch = true;
        }
        start = ++i;
        continue;
      }
      lastMatch = false;
      match = true;
      i++;
    }
    if (match || (preserveAllTokens && lastMatch)) {
      list.add(str.substring(start, i));
    }
    return (String[]) list.toArray(new String[list.size()]);
  }

  /**
   * <p>
   * Splits the provided text into an array, separators specified, preserving all
   * tokens, including empty tokens created by adjacent separators. This is an
   * alternative to using StringTokenizer.
   * </p>
   *
   * <p>
   * The separator is not included in the returned String array. Adjacent
   * separators are treated as separators for empty tokens. For more control over
   * the split use the StrTokenizer class.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>. A
   * <code>null</code> separatorChars splits on whitespace.
   * </p>
   *
   * <pre>
   * StringUtils.splitPreserveAllTokens(null, *)           = null
   * StringUtils.splitPreserveAllTokens("", *)             = []
   * StringUtils.splitPreserveAllTokens("abc def", null)   = ["abc", "def"]
   * StringUtils.splitPreserveAllTokens("abc def", " ")    = ["abc", "def"]
   * StringUtils.splitPreserveAllTokens("abc  def", " ")   = ["abc", "", def"]
   * StringUtils.splitPreserveAllTokens("ab:cd:ef", ":")   = ["ab", "cd", "ef"]
   * StringUtils.splitPreserveAllTokens("ab:cd:ef:", ":")  = ["ab", "cd", "ef", ""]
   * StringUtils.splitPreserveAllTokens("ab:cd:ef::", ":") = ["ab", "cd", "ef", "", ""]
   * StringUtils.splitPreserveAllTokens("ab::cd:ef", ":")  = ["ab", "", cd", "ef"]
   * StringUtils.splitPreserveAllTokens(":cd:ef", ":")     = ["", cd", "ef"]
   * StringUtils.splitPreserveAllTokens("::cd:ef", ":")    = ["", "", cd", "ef"]
   * StringUtils.splitPreserveAllTokens(":cd:ef:", ":")    = ["", cd", "ef", ""]
   * </pre>
   *
   * @param str            the String to parse, may be <code>null</code>
   * @param separatorChars the characters used as the delimiters,
   *                       <code>null</code> splits on whitespace
   * @return an array of parsed Strings, <code>null</code> if null String input
   * @since 2.1
   */
  public static String[] splitPreserveAllTokens(String str, String separatorChars) {
    return splitWorker(str, separatorChars, -1, true);
  }

  /**
   * <p>
   * Splits the provided text into an array with a maximum length, separators
   * specified, preserving all tokens, including empty tokens created by adjacent
   * separators.
   * </p>
   *
   * <p>
   * The separator is not included in the returned String array. Adjacent
   * separators are treated as separators for empty tokens. Adjacent separators
   * are treated as one separator.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>. A
   * <code>null</code> separatorChars splits on whitespace.
   * </p>
   *
   * <p>
   * If more than <code>max</code> delimited substrings are found, the last
   * returned string includes all characters after the first <code>max - 1</code>
   * returned strings (including separator characters).
   * </p>
   *
   * <pre>
   * StringUtils.splitPreserveAllTokens(null, *, *)            = null
   * StringUtils.splitPreserveAllTokens("", *, *)              = []
   * StringUtils.splitPreserveAllTokens("ab de fg", null, 0)   = ["ab", "cd", "ef"]
   * StringUtils.splitPreserveAllTokens("ab   de fg", null, 0) = ["ab", "cd", "ef"]
   * StringUtils.splitPreserveAllTokens("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
   * StringUtils.splitPreserveAllTokens("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
   * StringUtils.splitPreserveAllTokens("ab   de fg", null, 2) = ["ab", "  de fg"]
   * StringUtils.splitPreserveAllTokens("ab   de fg", null, 3) = ["ab", "", " de fg"]
   * StringUtils.splitPreserveAllTokens("ab   de fg", null, 4) = ["ab", "", "", "de fg"]
   * </pre>
   *
   * @param str            the String to parse, may be <code>null</code>
   * @param separatorChars the characters used as the delimiters,
   *                       <code>null</code> splits on whitespace
   * @param max            the maximum number of elements to include in the array.
   *                       A zero or negative value implies no limit
   * @return an array of parsed Strings, <code>null</code> if null String input
   * @since 2.1
   */
  public static String[] splitPreserveAllTokens(String str, String separatorChars, int max) {
    return splitWorker(str, separatorChars, max, true);
  }

  /**
   * Performs the logic for the <code>split</code> and
   * <code>splitPreserveAllTokens</code> methods that return a maximum array
   * length.
   *
   * @param str               the String to parse, may be <code>null</code>
   * @param separatorChars    the separate character
   * @param max               the maximum number of elements to include in the
   *                          array. A zero or negative value implies no limit.
   * @param preserveAllTokens if <code>true</code>, adjacent separators are
   *                          treated as empty token separators; if
   *                          <code>false</code>, adjacent separators are treated
   *                          as one separator.
   * @return an array of parsed Strings, <code>null</code> if null String input
   */
  private static String[] splitWorker(String str, String separatorChars, int max,
      boolean preserveAllTokens) {
    // Performance tuned for 2.0 (JDK1.4)
    // Direct code is quicker than StringTokenizer.
    // Also, StringTokenizer uses isSpace() not isWhitespace()

    if (str == null) {
      return null;
    }
    int len = str.length();
    if (len == 0) {
      return EMPTY_STRING_ARRAY;
    }
    List list = new ArrayList();
    int sizePlus1 = 1;
    int i = 0, start = 0;
    boolean match = false;
    boolean lastMatch = false;
    if (separatorChars == null) {
      // Null separator means use whitespace
      while (i < len) {
        if (Character.isWhitespace(str.charAt(i))) {
          if (match || preserveAllTokens) {
            lastMatch = true;
            if (sizePlus1++ == max) {
              i = len;
              lastMatch = false;
            }
            list.add(str.substring(start, i));
            match = false;
          }
          start = ++i;
          continue;
        }
        lastMatch = false;
        match = true;
        i++;
      }
    } else if (separatorChars.length() == 1) {
      // Optimise 1 character case
      char sep = separatorChars.charAt(0);
      while (i < len) {
        if (str.charAt(i) == sep) {
          if (match || preserveAllTokens) {
            lastMatch = true;
            if (sizePlus1++ == max) {
              i = len;
              lastMatch = false;
            }
            list.add(str.substring(start, i));
            match = false;
          }
          start = ++i;
          continue;
        }
        lastMatch = false;
        match = true;
        i++;
      }
    } else {
      // standard case
      while (i < len) {
        if (separatorChars.indexOf(str.charAt(i)) >= 0) {
          if (match || preserveAllTokens) {
            lastMatch = true;
            if (sizePlus1++ == max) {
              i = len;
              lastMatch = false;
            }
            list.add(str.substring(start, i));
            match = false;
          }
          start = ++i;
          continue;
        }
        lastMatch = false;
        match = true;
        i++;
      }
    }
    if (match || (preserveAllTokens && lastMatch)) {
      list.add(str.substring(start, i));
    }
    return (String[]) list.toArray(new String[list.size()]);
  }

  /**
   * <p>
   * Splits a String by Character type as returned by
   * <code>java.lang.Character.getType(char)</code>. Groups of contiguous
   * characters of the same type are returned as complete tokens.
   * 
   * <pre>
   * StringUtils.splitByCharacterType(null)         = null
   * StringUtils.splitByCharacterType("")           = []
   * StringUtils.splitByCharacterType("ab de fg")   = ["ab", " ", "de", " ", "fg"]
   * StringUtils.splitByCharacterType("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
   * StringUtils.splitByCharacterType("ab:cd:ef")   = ["ab", ":", "cd", ":", "ef"]
   * StringUtils.splitByCharacterType("number5")    = ["number", "5"]
   * StringUtils.splitByCharacterType("fooBar")     = ["foo", "B", "ar"]
   * StringUtils.splitByCharacterType("foo200Bar")  = ["foo", "200", "B", "ar"]
   * StringUtils.splitByCharacterType("ASFRules")   = ["ASFR", "ules"]
   * </pre>
   * 
   * @param str the String to split, may be <code>null</code>
   * @return an array of parsed Strings, <code>null</code> if null String input
   * @since 2.4
   */
  public static String[] splitByCharacterType(String str) {
    return splitByCharacterType(str, false);
  }

  /**
   * <p>
   * Splits a String by Character type as returned by
   * <code>java.lang.Character.getType(char)</code>. Groups of contiguous
   * characters of the same type are returned as complete tokens, with the
   * following exception: the character of type
   * <code>Character.UPPERCASE_LETTER</code>, if any, immediately preceding a
   * token of type <code>Character.LOWERCASE_LETTER</code> will belong to the
   * following token rather than to the preceding, if any,
   * <code>Character.UPPERCASE_LETTER</code> token.
   * 
   * <pre>
   * StringUtils.splitByCharacterTypeCamelCase(null)         = null
   * StringUtils.splitByCharacterTypeCamelCase("")           = []
   * StringUtils.splitByCharacterTypeCamelCase("ab de fg")   = ["ab", " ", "de", " ", "fg"]
   * StringUtils.splitByCharacterTypeCamelCase("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
   * StringUtils.splitByCharacterTypeCamelCase("ab:cd:ef")   = ["ab", ":", "cd", ":", "ef"]
   * StringUtils.splitByCharacterTypeCamelCase("number5")    = ["number", "5"]
   * StringUtils.splitByCharacterTypeCamelCase("fooBar")     = ["foo", "Bar"]
   * StringUtils.splitByCharacterTypeCamelCase("foo200Bar")  = ["foo", "200", "Bar"]
   * StringUtils.splitByCharacterTypeCamelCase("ASFRules")   = ["ASF", "Rules"]
   * </pre>
   * 
   * @param str the String to split, may be <code>null</code>
   * @return an array of parsed Strings, <code>null</code> if null String input
   * @since 2.4
   */
  public static String[] splitByCharacterTypeCamelCase(String str) {
    return splitByCharacterType(str, true);
  }

  /**
   * <p>
   * Splits a String by Character type as returned by
   * <code>java.lang.Character.getType(char)</code>. Groups of contiguous
   * characters of the same type are returned as complete tokens, with the
   * following exception: if <code>camelCase</code> is <code>true</code>, the
   * character of type <code>Character.UPPERCASE_LETTER</code>, if any,
   * immediately preceding a token of type <code>Character.LOWERCASE_LETTER</code>
   * will belong to the following token rather than to the preceding, if any,
   * <code>Character.UPPERCASE_LETTER</code> token.
   * 
   * @param str       the String to split, may be <code>null</code>
   * @param camelCase whether to use so-called "camel-case" for letter types
   * @return an array of parsed Strings, <code>null</code> if null String input
   * @since 2.4
   */
  private static String[] splitByCharacterType(String str, boolean camelCase) {
    if (str == null) {
      return null;
    }
    if (str.length() == 0) {
      return EMPTY_STRING_ARRAY;
    }
    char[] c = str.toCharArray();
    List list = new ArrayList();
    int tokenStart = 0;
    int currentType = Character.getType(c[tokenStart]);
    for (int pos = tokenStart + 1; pos < c.length; pos++) {
      int type = Character.getType(c[pos]);
      if (type == currentType) {
        continue;
      }
      if (camelCase && type == Character.LOWERCASE_LETTER
          && currentType == Character.UPPERCASE_LETTER) {
        int newTokenStart = pos - 1;
        if (newTokenStart != tokenStart) {
          list.add(new String(c, tokenStart, newTokenStart - tokenStart));
          tokenStart = newTokenStart;
        }
      } else {
        list.add(new String(c, tokenStart, pos - tokenStart));
        tokenStart = pos;
      }
      currentType = type;
    }
    list.add(new String(c, tokenStart, c.length - tokenStart));
    return (String[]) list.toArray(new String[list.size()]);
  }

  /**
   * <p>
   * Removes control characters (char &lt;= 32) from both ends of this String,
   * handling <code>null</code> by returning <code>null</code>.
   * </p>
   *
   * <p>
   * The String is trimmed using {@link String#trim()}. Trim removes start and end
   * characters &lt;= 32. To strip whitespace use {@link #strip(String)}.
   * </p>
   *
   * <p>
   * To trim your choice of characters, use the {@link #strip(String, String)}
   * methods.
   * </p>
   *
   * <pre>
   * StringUtils.trim(null)          = null
   * StringUtils.trim("")            = ""
   * StringUtils.trim("     ")       = ""
   * StringUtils.trim("abc")         = "abc"
   * StringUtils.trim("    abc    ") = "abc"
   * </pre>
   *
   * @param str the String to be trimmed, may be null
   * @return the trimmed string, <code>null</code> if null String input
   */
  public static String trim(String str) {
    return str == null ? null : str.trim();
  }

  /**
   * <p>
   * Removes control characters (char &lt;= 32) from both ends of this String
   * returning <code>null</code> if the String is empty ("") after the trim or if
   * it is <code>null</code>.
   * 
   * <p>
   * The String is trimmed using {@link String#trim()}. Trim removes start and end
   * characters &lt;= 32. To strip whitespace use
   *
   * @param str the String to be trimmed, may be null
   * @return the trimmed String, <code>null</code> if only chars &lt;= 32, empty
   *         or null String input {@link #stripToNull(String)}.
   *         </p>
   * 
   *         <pre>
   * StringUtils.trimToNull(null)          = null
   * StringUtils.trimToNull("")            = null
   * StringUtils.trimToNull("     ")       = null
   * StringUtils.trimToNull("abc")         = "abc"
   * StringUtils.trimToNull("    abc    ") = "abc"
   *         </pre>
   * 
   * @since 2.0
   */
  public static String trimToNull(String str) {
    String ts = trim(str);
    return isEmpty(ts) ? null : ts;
  }

  /**
   * <p>
   * Removes control characters (char &lt;= 32) from both ends of this String
   * returning an empty String ("") if the String is empty ("") after the trim or
   * if it is <code>null</code>.
   * 
   * <p>
   * The String is trimmed using {@link String#trim()}. Trim removes start and end
   * characters &lt;= 32. To strip whitespace use
   *
   * @param str the String to be trimmed, may be null
   * @return the trimmed String, or an empty String if <code>null</code> input
   *         {@link #stripToEmpty(String)}.
   *         </p>
   * 
   *         <pre>
   * StringUtils.trimToEmpty(null)          = ""
   * StringUtils.trimToEmpty("")            = ""
   * StringUtils.trimToEmpty("     ")       = ""
   * StringUtils.trimToEmpty("abc")         = "abc"
   * StringUtils.trimToEmpty("    abc    ") = "abc"
   *         </pre>
   * 
   * @since 2.0
   */
  public static String trimToEmpty(String str) {
    return str == null ? EMPTY : str.trim();
  }

  // Stripping
  // -----------------------------------------------------------------------
  /**
   * <p>
   * Strips whitespace from the start and end of a String.
   * </p>
   *
   * <p>
   * This is similar to {@link #trim(String)} but removes whitespace. Whitespace
   * is defined by {@link Character#isWhitespace(char)}.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>.
   * </p>
   *
   * <pre>
   * StringUtils.strip(null)     = null
   * StringUtils.strip("")       = ""
   * StringUtils.strip("   ")    = ""
   * StringUtils.strip("abc")    = "abc"
   * StringUtils.strip("  abc")  = "abc"
   * StringUtils.strip("abc  ")  = "abc"
   * StringUtils.strip(" abc ")  = "abc"
   * StringUtils.strip(" ab c ") = "ab c"
   * </pre>
   *
   * @param str the String to remove whitespace from, may be null
   * @return the stripped String, <code>null</code> if null String input
   */
  public static String strip(String str) {
    return strip(str, null);
  }

  /**
   * <p>
   * Strips whitespace from the start and end of a String returning
   * <code>null</code> if the String is empty ("") after the strip.
   * </p>
   *
   * <p>
   * This is similar to {@link #trimToNull(String)} but removes whitespace.
   * Whitespace is defined by {@link Character#isWhitespace(char)}.
   * </p>
   *
   * <pre>
   * StringUtils.stripToNull(null)     = null
   * StringUtils.stripToNull("")       = null
   * StringUtils.stripToNull("   ")    = null
   * StringUtils.stripToNull("abc")    = "abc"
   * StringUtils.stripToNull("  abc")  = "abc"
   * StringUtils.stripToNull("abc  ")  = "abc"
   * StringUtils.stripToNull(" abc ")  = "abc"
   * StringUtils.stripToNull(" ab c ") = "ab c"
   * </pre>
   *
   * @param str the String to be stripped, may be null
   * @return the stripped String, <code>null</code> if whitespace, empty or null
   *         String input
   * @since 2.0
   */
  public static String stripToNull(String str) {
    if (str == null) {
      return null;
    }
    str = strip(str, null);
    return str.length() == 0 ? null : str;
  }

  /**
   * <p>
   * Strips whitespace from the start and end of a String returning an empty
   * String if <code>null</code> input.
   * </p>
   *
   * <p>
   * This is similar to {@link #trimToEmpty(String)} but removes whitespace.
   * Whitespace is defined by {@link Character#isWhitespace(char)}.
   * </p>
   *
   * <pre>
   * StringUtils.stripToEmpty(null)     = ""
   * StringUtils.stripToEmpty("")       = ""
   * StringUtils.stripToEmpty("   ")    = ""
   * StringUtils.stripToEmpty("abc")    = "abc"
   * StringUtils.stripToEmpty("  abc")  = "abc"
   * StringUtils.stripToEmpty("abc  ")  = "abc"
   * StringUtils.stripToEmpty(" abc ")  = "abc"
   * StringUtils.stripToEmpty(" ab c ") = "ab c"
   * </pre>
   *
   * @param str the String to be stripped, may be null
   * @return the trimmed String, or an empty String if <code>null</code> input
   * @since 2.0
   */
  public static String stripToEmpty(String str) {
    return str == null ? EMPTY : strip(str, null);
  }

  /**
   * <p>
   * Strips any of a set of characters from the start and end of a String. This is
   * similar to {@link String#trim()} but allows the characters to be stripped to
   * be controlled.
   * </p>
   * 
   * <p>
   * A <code>null</code> input String returns <code>null</code>. An empty string
   * ("") input returns the empty string.
   * </p>
   * 
   * <p>
   * If the stripChars String is <code>null</code>, whitespace is stripped as
   * defined by {@link Character#isWhitespace(char)}. Alternatively use
   *
   * @param str        the String to remove characters from, may be null
   * @param stripChars the characters to remove, null treated as whitespace
   * @return the stripped String, <code>null</code> if null String input
   *         {@link #strip(String)}.
   *         </p>
   * 
   *         <pre>
   * StringUtils.strip(null, *)          = null
   * StringUtils.strip("", *)            = ""
   * StringUtils.strip("abc", null)      = "abc"
   * StringUtils.strip("  abc", null)    = "abc"
   * StringUtils.strip("abc  ", null)    = "abc"
   * StringUtils.strip(" abc ", null)    = "abc"
   * StringUtils.strip("  abcyx", "xyz") = "  abc"
   *         </pre>
   */
  public static String strip(String str, String stripChars) {
    if (isEmpty(str)) {
      return str;
    }
    str = stripStart(str, stripChars);
    return stripEnd(str, stripChars);
  }

  /**
   * <p>
   * Strips any of a set of characters from the start of a String.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>. An empty string
   * ("") input returns the empty string.
   * </p>
   *
   * <p>
   * If the stripChars String is <code>null</code>, whitespace is stripped as
   * defined by {@link Character#isWhitespace(char)}.
   * </p>
   *
   * <pre>
   * StringUtils.stripStart(null, *)          = null
   * StringUtils.stripStart("", *)            = ""
   * StringUtils.stripStart("abc", "")        = "abc"
   * StringUtils.stripStart("abc", null)      = "abc"
   * StringUtils.stripStart("  abc", null)    = "abc"
   * StringUtils.stripStart("abc  ", null)    = "abc  "
   * StringUtils.stripStart(" abc ", null)    = "abc "
   * StringUtils.stripStart("yxabc  ", "xyz") = "abc  "
   * </pre>
   *
   * @param str        the String to remove characters from, may be null
   * @param stripChars the characters to remove, null treated as whitespace
   * @return the stripped String, <code>null</code> if null String input
   */
  public static String stripStart(String str, String stripChars) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0) {
      return str;
    }
    int start = 0;
    if (stripChars == null) {
      while ((start != strLen) && Character.isWhitespace(str.charAt(start))) {
        start++;
      }
    } else if (stripChars.length() == 0) {
      return str;
    } else {
      while ((start != strLen) && (stripChars.indexOf(str.charAt(start)) != INDEX_NOT_FOUND)) {
        start++;
      }
    }
    return str.substring(start);
  }

  /**
   * <p>
   * Strips any of a set of characters from the end of a String.
   * </p>
   *
   * <p>
   * A <code>null</code> input String returns <code>null</code>. An empty string
   * ("") input returns the empty string.
   * </p>
   *
   * <p>
   * If the stripChars String is <code>null</code>, whitespace is stripped as
   * defined by {@link Character#isWhitespace(char)}.
   * </p>
   *
   * <pre>
   * StringUtils.stripEnd(null, *)          = null
   * StringUtils.stripEnd("", *)            = ""
   * StringUtils.stripEnd("abc", "")        = "abc"
   * StringUtils.stripEnd("abc", null)      = "abc"
   * StringUtils.stripEnd("  abc", null)    = "  abc"
   * StringUtils.stripEnd("abc  ", null)    = "abc"
   * StringUtils.stripEnd(" abc ", null)    = " abc"
   * StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
   * StringUtils.stripEnd("120.00", ".0")   = "12"
   * </pre>
   *
   * @param str        the String to remove characters from, may be null
   * @param stripChars the set of characters to remove, null treated as whitespace
   * @return the stripped String, <code>null</code> if null String input
   */
  public static String stripEnd(String str, String stripChars) {
    int end;
    if (str == null || (end = str.length()) == 0) {
      return str;
    }

    if (stripChars == null) {
      while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
        end--;
      }
    } else if (stripChars.length() == 0) {
      return str;
    } else {
      while ((end != 0) && (stripChars.indexOf(str.charAt(end - 1)) != INDEX_NOT_FOUND)) {
        end--;
      }
    }
    return str.substring(0, end);
  }

  // StripAll
  // -----------------------------------------------------------------------
  /**
   * <p>
   * Strips whitespace from the start and end of every String in an array.
   * Whitespace is defined by {@link Character#isWhitespace(char)}.
   * </p>
   *
   * <p>
   * A new array is returned each time, except for length zero. A
   * <code>null</code> array will return <code>null</code>. An empty array will
   * return itself. A <code>null</code> array entry will be ignored.
   * </p>
   *
   * <pre>
   * StringUtils.stripAll(null)             = null
   * StringUtils.stripAll([])               = []
   * StringUtils.stripAll(["abc", "  abc"]) = ["abc", "abc"]
   * StringUtils.stripAll(["abc  ", null])  = ["abc", null]
   * </pre>
   *
   * @param strs the array to remove whitespace from, may be null
   * @return the stripped Strings, <code>null</code> if null array input
   */
  public static String[] stripAll(String[] strs) {
    return stripAll(strs, null);
  }

  /**
   * <p>
   * Strips any of a set of characters from the start and end of every String in
   * an array.
   * </p>
   * Whitespace is defined by {@link Character#isWhitespace(char)}.
   * </p>
   * 
   * <p>
   * A new array is returned each time, except for length zero. A
   * <code>null</code> array will return <code>null</code>. An empty array will
   * return itself. A <code>null</code> array entry will be ignored. A
   * <code>null</code> stripChars will strip whitespace as defined by
   *
   * @param strs       the array to remove characters from, may be null
   * @param stripChars the characters to remove, null treated as whitespace
   * @return the stripped Strings, <code>null</code> if null array input
   *         {@link Character#isWhitespace(char)}.
   *         </p>
   * 
   *         <pre>
   * StringUtils.stripAll(null, *)                = null
   * StringUtils.stripAll([], *)                  = []
   * StringUtils.stripAll(["abc", "  abc"], null) = ["abc", "abc"]
   * StringUtils.stripAll(["abc  ", null], null)  = ["abc", null]
   * StringUtils.stripAll(["abc  ", null], "yz")  = ["abc  ", null]
   * StringUtils.stripAll(["yabcz", null], "yz")  = ["abc", null]
   *         </pre>
   */
  public static String[] stripAll(String[] strs, String stripChars) {
    int strsLen;
    if (strs == null || (strsLen = strs.length) == 0) {
      return strs;
    }
    String[] newArr = new String[strsLen];
    for (int i = 0; i < strsLen; i++) {
      newArr[i] = strip(strs[i], stripChars);
    }
    return newArr;
  }

  // startsWith
  // -----------------------------------------------------------------------

  /**
   * <p>
   * Check if a String starts with a specified prefix.
   * </p>
   * 
   * <p>
   * <code>null</code>s are handled without exceptions. Two <code>null</code>
   * references are considered to be equal. The comparison is case sensitive.
   * </p>
   * 
   * <pre>
   * StringUtils.startsWith(null, null)      = true
   * StringUtils.startsWith(null, "abc")     = false
   * StringUtils.startsWith("abcdef", null)  = false
   * StringUtils.startsWith("abcdef", "abc") = true
   * StringUtils.startsWith("ABCDEF", "abc") = false
   * </pre>
   *
   * @param str    the String to check, may be null
   * @param prefix the prefix to find, may be null
   * @return <code>true</code> if the String starts with the prefix, case
   *         sensitive, or both <code>null</code>
   * @see java.lang.String#startsWith(String)
   * @since 2.4
   */
  public static boolean startsWith(String str, String prefix) {
    return startsWith(str, prefix, false);
  }

  /**
   * <p>
   * Case insensitive check if a String starts with a specified prefix.
   * </p>
   * 
   * <p>
   * <code>null</code>s are handled without exceptions. Two <code>null</code>
   * references are considered to be equal. The comparison is case insensitive.
   * </p>
   * 
   * <pre>
   * StringUtils.startsWithIgnoreCase(null, null)      = true
   * StringUtils.startsWithIgnoreCase(null, "abc")     = false
   * StringUtils.startsWithIgnoreCase("abcdef", null)  = false
   * StringUtils.startsWithIgnoreCase("abcdef", "abc") = true
   * StringUtils.startsWithIgnoreCase("ABCDEF", "abc") = true
   * </pre>
   *
   * @param str    the String to check, may be null
   * @param prefix the prefix to find, may be null
   * @return <code>true</code> if the String starts with the prefix, case
   *         insensitive, or both <code>null</code>
   * @see java.lang.String#startsWith(String)
   * @since 2.4
   */
  public static boolean startsWithIgnoreCase(String str, String prefix) {
    return startsWith(str, prefix, true);
  }

  /**
   * <p>
   * Check if a String starts with a specified prefix (optionally case
   * insensitive).
   * </p>
   *
   * @param str        the String to check, may be null
   * @param prefix     the prefix to find, may be null
   * @param ignoreCase inidicates whether the compare should ignore case (case
   *                   insensitive) or not.
   * @return <code>true</code> if the String starts with the prefix or both
   *         <code>null</code>
   * @see java.lang.String#startsWith(String)
   */
  private static boolean startsWith(String str, String prefix, boolean ignoreCase) {
    if (str == null || prefix == null) {
      return (str == null && prefix == null);
    }
    if (prefix.length() > str.length()) {
      return false;
    }
    return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
  }

  /**
   * <p>
   * Check if a String starts with any of an array of specified strings.
   * </p>
   * 
   * <pre>
   * StringUtils.startsWithAny(null, null)      = false
   * StringUtils.startsWithAny(null, new String[] {"abc"})  = false
   * StringUtils.startsWithAny("abcxyz", null)     = false
   * StringUtils.startsWithAny("abcxyz", new String[] {""}) = false
   * StringUtils.startsWithAny("abcxyz", new String[] {"abc"}) = true
   * StringUtils.startsWithAny("abcxyz", new String[] {null, "xyz", "abc"}) = true
   * </pre>
   *
   * @param string        the String to check, may be null
   * @param searchStrings the Strings to find, may be null or empty
   * @return <code>true</code> if the String starts with any of the the prefixes,
   *         case insensitive, or both <code>null</code>
   * @see #startsWith(String, String)
   * @since 2.5
   */
  public static boolean startsWithAny(String string, String[] searchStrings) {
    if (isEmpty(string) || isEmpty(searchStrings)) {
      return false;
    }
    for (int i = 0; i < searchStrings.length; i++) {
      String searchString = searchStrings[i];
      if (startsWith(string, searchString)) {
        return true;
      }
    }
    return false;
  }

  // endsWith
  // -----------------------------------------------------------------------

  /**
   * Checks if is empty.
   *
   * @param searchStrings the search strings
   * @return true, if checks if is empty
   */
  public static boolean isEmpty(String[] searchStrings) {
    return searchStrings == null || searchStrings.length == 0;
  }

  /**
   * <p>
   * Check if a String ends with a specified suffix.
   * </p>
   * 
   * <p>
   * <code>null</code>s are handled without exceptions. Two <code>null</code>
   * references are considered to be equal. The comparison is case sensitive.
   * </p>
   * 
   * <pre>
   * StringUtils.endsWith(null, null)      = true
   * StringUtils.endsWith(null, "def")     = false
   * StringUtils.endsWith("abcdef", null)  = false
   * StringUtils.endsWith("abcdef", "def") = true
   * StringUtils.endsWith("ABCDEF", "def") = false
   * StringUtils.endsWith("ABCDEF", "cde") = false
   * </pre>
   *
   * @param str    the String to check, may be null
   * @param suffix the suffix to find, may be null
   * @return <code>true</code> if the String ends with the suffix, case sensitive,
   *         or both <code>null</code>
   * @see java.lang.String#endsWith(String)
   * @since 2.4
   */
  public static boolean endsWith(String str, String suffix) {
    return endsWith(str, suffix, false);
  }

  /**
   * <p>
   * Case insensitive check if a String ends with a specified suffix.
   * </p>
   * 
   * <p>
   * <code>null</code>s are handled without exceptions. Two <code>null</code>
   * references are considered to be equal. The comparison is case insensitive.
   * </p>
   * 
   * <pre>
   * StringUtils.endsWithIgnoreCase(null, null)      = true
   * StringUtils.endsWithIgnoreCase(null, "def")     = false
   * StringUtils.endsWithIgnoreCase("abcdef", null)  = false
   * StringUtils.endsWithIgnoreCase("abcdef", "def") = true
   * StringUtils.endsWithIgnoreCase("ABCDEF", "def") = true
   * StringUtils.endsWithIgnoreCase("ABCDEF", "cde") = false
   * </pre>
   *
   * @param str    the String to check, may be null
   * @param suffix the suffix to find, may be null
   * @return <code>true</code> if the String ends with the suffix, case
   *         insensitive, or both <code>null</code>
   * @see java.lang.String#endsWith(String)
   * @since 2.4
   */
  public static boolean endsWithIgnoreCase(String str, String suffix) {
    return endsWith(str, suffix, true);
  }

  /**
   * <p>
   * Check if a String ends with a specified suffix (optionally case insensitive).
   * </p>
   *
   * @param str        the String to check, may be null
   * @param suffix     the suffix to find, may be null
   * @param ignoreCase inidicates whether the compare should ignore case (case
   *                   insensitive) or not.
   * @return <code>true</code> if the String starts with the prefix or both
   *         <code>null</code>
   * @see java.lang.String#endsWith(String)
   */
  private static boolean endsWith(String str, String suffix, boolean ignoreCase) {
    if (str == null || suffix == null) {
      return (str == null && suffix == null);
    }
    if (suffix.length() > str.length()) {
      return false;
    }
    int strOffset = str.length() - suffix.length();
    return str.regionMatches(ignoreCase, strOffset, suffix, 0, suffix.length());
  }

  // Preconditions{
  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   *
   * @param expression a boolean expression
   */
  public static void checkArgument(boolean expression) {
    if (!expression) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   *
   * @param expression   a boolean expression
   * @param errorMessage the exception message to use if the check fails; will be
   *                     converted to a string using
   *                     {@link String#valueOf(Object)}
   */
  public static void checkArgument(boolean expression, Object errorMessage) {
    if (!expression) {
      throw new IllegalArgumentException(String.valueOf(errorMessage));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   *
   * @param expression           a boolean expression
   * @param errorMessageTemplate a template for the exception message should the
   *                             check fail. The message is formed by replacing
   *                             each {@code %s} placeholder in the template with
   *                             an argument. These are matched by position - the
   *                             first
   * @param errorMessageArgs     the arguments to be substituted into the message
   *                             template. Arguments are converted to strings
   *                             using {@link String#valueOf(Object)}. {@code %s}
   *                             gets {@code
   * errorMessageArgs[0]}     , etc. Unmatched arguments will be appended to
   *                             the formatted message in square braces. Unmatched
   *                             placeholders will be left as-is.
   *                             {@code errorMessageArgs} is null (don't let this
   *                             happen)
   */
  public static void checkArgument(boolean expression, String errorMessageTemplate,
      Object... errorMessageArgs) {
    if (!expression) {
      throw new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, char p1) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, int p1) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, long p1) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, Object p1) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, char p1, char p2) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, char p1, int p2) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, char p1, long p2) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, char p1, Object p2) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, int p1, char p2) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, int p1, int p2) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, int p1, long p2) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, int p1, Object p2) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, long p1, char p2) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, long p1, int p2) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, long p1, long p2) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, long p1, Object p2) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, Object p1, char p2) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, Object p1, int p2) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, Object p1, long p2) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, Object p1, Object p2) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @param p3                   the p3
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, Object p1, Object p2,
      Object p3) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2, p3));
    }
  }

  /**
   * Ensures the truth of an expression involving one or more parameters to the
   * calling method.
   * 
   * <p>
   * See {@link #checkArgument(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @param p3                   the p3
   * @param p4                   the p4
   */
  public static void checkArgument(boolean b, String errorMessageTemplate, Object p1, Object p2,
      Object p3, Object p4) {
    if (!b) {
      throw new IllegalArgumentException(format(errorMessageTemplate, p1, p2, p3, p4));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   *
   * @param expression a boolean expression
   */
  public static void checkState(boolean expression) {
    if (!expression) {
      throw new IllegalStateException();
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   *
   * @param expression   a boolean expression
   * @param errorMessage the exception message to use if the check fails; will be
   *                     converted to a string using
   *                     {@link String#valueOf(Object)}
   */
  public static void checkState(boolean expression, Object errorMessage) {
    if (!expression) {
      throw new IllegalStateException(String.valueOf(errorMessage));
    }
  }

  /**
   * Check state.
   *
   * @param status       the status
   * @param expression   the expression
   * @param errorMessage the error message
   */
  public static void checkState(int status, boolean expression, Object errorMessage) {
    if (!expression) {
      throw new DataException(status, String.valueOf(errorMessage));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   *
   * @param expression           a boolean expression
   * @param errorMessageTemplate a template for the exception message should the
   *                             check fail. The message is formed by replacing
   *                             each {@code %s} placeholder in the template with
   *                             an argument. These are matched by position - the
   *                             first
   * @param errorMessageArgs     the arguments to be substituted into the message
   *                             template. Arguments are converted to strings
   *                             using {@link String#valueOf(Object)}. {@code %s}
   *                             gets {@code
   * errorMessageArgs[0]}     , etc. Unmatched arguments will be appended to
   *                             the formatted message in square braces. Unmatched
   *                             placeholders will be left as-is.
   *                             {@code errorMessageArgs} is null (don't let this
   *                             happen)
   */
  public static void checkState(boolean expression, String errorMessageTemplate,
      Object... errorMessageArgs) {
    if (!expression) {
      throw new IllegalStateException(format(errorMessageTemplate, errorMessageArgs));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   */
  public static void checkState(boolean b, String errorMessageTemplate, char p1) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   */
  public static void checkState(boolean b, String errorMessageTemplate, int p1) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   */
  public static void checkState(boolean b, String errorMessageTemplate, long p1) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   */
  public static void checkState(boolean b, String errorMessageTemplate, Object p1) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkState(boolean b, String errorMessageTemplate, char p1, char p2) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkState(boolean b, String errorMessageTemplate, char p1, int p2) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkState(boolean b, String errorMessageTemplate, char p1, long p2) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkState(boolean b, String errorMessageTemplate, char p1, Object p2) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkState(boolean b, String errorMessageTemplate, int p1, char p2) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkState(boolean b, String errorMessageTemplate, int p1, int p2) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkState(boolean b, String errorMessageTemplate, int p1, long p2) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkState(boolean b, String errorMessageTemplate, int p1, Object p2) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkState(boolean b, String errorMessageTemplate, long p1, char p2) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkState(boolean b, String errorMessageTemplate, long p1, int p2) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkState(boolean b, String errorMessageTemplate, long p1, long p2) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkState(boolean b, String errorMessageTemplate, long p1, Object p2) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkState(boolean b, String errorMessageTemplate, Object p1, char p2) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkState(boolean b, String errorMessageTemplate, Object p1, int p2) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkState(boolean b, String errorMessageTemplate, Object p1, long p2) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   */
  public static void checkState(boolean b, String errorMessageTemplate, Object p1, Object p2) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @param p3                   the p3
   */
  public static void checkState(boolean b, String errorMessageTemplate, Object p1, Object p2,
      Object p3) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2, p3));
    }
  }

  /**
   * Ensures the truth of an expression involving the state of the calling
   * instance, but not involving any parameters to the calling method.
   * 
   * <p>
   * See {@link #checkState(boolean, String, Object...)} for details.
   *
   * @param b                    the b
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @param p3                   the p3
   * @param p4                   the p4
   */
  public static void checkState(boolean b, String errorMessageTemplate, Object p1, Object p2,
      Object p3, Object p4) {
    if (!b) {
      throw new IllegalStateException(format(errorMessageTemplate, p1, p2, p3, p4));
    }
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   *
   * @param <T>       the generic type
   * @param reference an object reference
   * @return the non-null reference that was validated
   */

  public static <T> T checkNotNull(T reference) {
    if (reference == null) {
      throw new NullPointerException();
    }
    return reference;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   *
   * @param <T>          the generic type
   * @param reference    an object reference
   * @param errorMessage the exception message to use if the check fails; will be
   *                     converted to a string using
   *                     {@link String#valueOf(Object)}
   * @return the non-null reference that was validated
   */

  public static <T> T checkNotNull(T reference, Object errorMessage) {
    if (reference == null) {
      throw new NullPointerException(String.valueOf(errorMessage));
    }
    return reference;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   *
   * @param <T>                  the generic type
   * @param reference            an object reference
   * @param errorMessageTemplate a template for the exception message should the
   *                             check fail. The message is formed by replacing
   *                             each {@code %s} placeholder in the template with
   *                             an argument. These are matched by position - the
   *                             first
   * @param errorMessageArgs     the arguments to be substituted into the message
   *                             template. Arguments are converted to strings
   *                             using {@link String#valueOf(Object)}.
   * @return the non-null reference that was validated {@code %s} gets {@code
   * errorMessageArgs[0]}, etc. Unmatched arguments will be appended to the
   *         formatted message in square braces. Unmatched placeholders will be
   *         left as-is.
   */

  public static <T> T checkNotNull(T reference, String errorMessageTemplate,
      Object... errorMessageArgs) {
    if (reference == null) {
      // If either of these parameters is null, the right thing happens anyway
      throw new NullPointerException(format(errorMessageTemplate, errorMessageArgs));
    }
    return reference;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, char p1) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, int p1) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, long p1) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, Object p1) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, char p1, char p2) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, char p1, int p2) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, char p1, long p2) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, char p1, Object p2) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, int p1, char p2) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, int p1, int p2) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, int p1, long p2) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, int p1, Object p2) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, long p1, char p2) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, long p1, int p2) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, long p1, long p2) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, long p1, Object p2) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, Object p1, char p2) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, Object p1, int p2) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, Object p1, long p2) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, Object p1, Object p2) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @param p3                   the p3
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, Object p1, Object p2,
      Object p3) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2, p3));
    }
    return obj;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method
   * is not null.
   * 
   * <p>
   * See {@link #checkNotNull(Object, String, Object...)} for details.
   *
   * @param <T>                  the generic type
   * @param obj                  the obj
   * @param errorMessageTemplate the error message template
   * @param p1                   the p1
   * @param p2                   the p2
   * @param p3                   the p3
   * @param p4                   the p4
   * @return the t
   */

  public static <T> T checkNotNull(T obj, String errorMessageTemplate, Object p1, Object p2,
      Object p3, Object p4) {
    if (obj == null) {
      throw new NullPointerException(format(errorMessageTemplate, p1, p2, p3, p4));
    }
    return obj;
  }

  /*
   * All recent hotspots (as of 2009) *really* like to have the natural code
   *
   * if (guardExpression) { throw new BadException(messageExpression); }
   *
   * refactored so that messageExpression is moved to a separate String-returning
   * method.
   *
   * if (guardExpression) { throw new BadException(badMsg(...)); }
   *
   * The alternative natural refactorings into void or Exception-returning methods
   * are much slower. This is a big deal - we're talking factors of 2-8 in
   * microbenchmarks, not just 10-20%. (This is a hotspot optimizer bug, which
   * should be fixed, but that's a separate, big project).
   *
   * The coding pattern above is heavily used in java.util, e.g. in ArrayList.
   * There is a RangeCheckMicroBenchmark in the JDK that was used to test this.
   *
   * But the methods in this class want to throw different exceptions, depending
   * on the args, so it appears that this pattern is not directly applicable. But
   * we can use the ridiculous, devious trick of throwing an exception in the
   * middle of the construction of another exception. Hotspot is fine with that.
   */

  /**
   * Ensures that {@code index} specifies a valid <i>element</i> in an array, list
   * or string of size {@code size}. An element index may range from zero,
   * inclusive, to {@code size}, exclusive.
   *
   * @param index a user-supplied index identifying an element of an array, list
   *              or string
   * @param size  the size of that array, list or string
   * @return the value of {@code index}
   */

  public static int checkElementIndex(int index, int size) {
    return checkElementIndex(index, size, "index");
  }

  /**
   * Ensures that {@code index} specifies a valid <i>element</i> in an array, list
   * or string of size {@code size}. An element index may range from zero,
   * inclusive, to {@code size}, exclusive.
   *
   * @param index a user-supplied index identifying an element of an array, list
   *              or string
   * @param size  the size of that array, list or string
   * @param desc  the text to use to describe this index in an error message
   * @return the value of {@code index}
   */

  public static int checkElementIndex(int index, int size, String desc) {
    // Carefully optimized for execution by hotspot (explanatory comment above)
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(badElementIndex(index, size, desc));
    }
    return index;
  }

  /**
   * Bad element index.
   *
   * @param index the index
   * @param size  the size
   * @param desc  the desc
   * @return the string
   */
  private static String badElementIndex(int index, int size, String desc) {
    if (index < 0) {
      return format("%s (%s) must not be negative", desc, index);
    } else if (size < 0) {
      throw new IllegalArgumentException("negative size: " + size);
    } else { // index >= size
      return format("%s (%s) must be less than size (%s)", desc, index, size);
    }
  }

  /**
   * Ensures that {@code index} specifies a valid <i>position</i> in an array,
   * list or string of size {@code size}. A position index may range from zero to
   *
   * @param index a user-supplied index identifying a position in an array, list
   *              or string
   * @param size  the size of that array, list or string
   * @return the value of {@code index} {@code size}, inclusive.
   */

  public static int checkPositionIndex(int index, int size) {
    return checkPositionIndex(index, size, "index");
  }

  /**
   * Ensures that {@code index} specifies a valid <i>position</i> in an array,
   * list or string of size {@code size}. A position index may range from zero to
   *
   * @param index a user-supplied index identifying a position in an array, list
   *              or string
   * @param size  the size of that array, list or string
   * @param desc  the text to use to describe this index in an error message
   * @return the value of {@code index} {@code size}, inclusive.
   */

  public static int checkPositionIndex(int index, int size, String desc) {
    // Carefully optimized for execution by hotspot (explanatory comment above)
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException(badPositionIndex(index, size, desc));
    }
    return index;
  }

  /**
   * Bad position index.
   *
   * @param index the index
   * @param size  the size
   * @param desc  the desc
   * @return the string
   */
  private static String badPositionIndex(int index, int size, String desc) {
    if (index < 0) {
      return format("%s (%s) must not be negative", desc, index);
    } else if (size < 0) {
      throw new IllegalArgumentException("negative size: " + size);
    } else { // index > size
      return format("%s (%s) must not be greater than size (%s)", desc, index, size);
    }
  }

  /**
   * Ensures that {@code start} and {@code end} specify a valid <i>positions</i>
   * in an array, list or string of size {@code size}, and are in order. A
   * position index may range from zero to {@code size}, inclusive.
   *
   * @param start a user-supplied index identifying a starting position in an
   *              array, list or string
   * @param end   a user-supplied index identifying a ending position in an array,
   *              list or string
   * @param size  the size of that array, list or string {@code end} is less than
   *              {@code start}
   */
  public static void checkPositionIndexes(int start, int end, int size) {
    // Carefully optimized for execution by hotspot (explanatory comment above)
    if (start < 0 || end < start || end > size) {
      throw new IndexOutOfBoundsException(badPositionIndexes(start, end, size));
    }
  }

  /**
   * Bad position indexes.
   *
   * @param start the start
   * @param end   the end
   * @param size  the size
   * @return the string
   */
  private static String badPositionIndexes(int start, int end, int size) {
    if (start < 0 || start > size) {
      return badPositionIndex(start, size, "start index");
    }
    if (end < 0 || end > size) {
      return badPositionIndex(end, size, "end index");
    }
    // end < start
    return format("end index (%s) must not be less than start index (%s)", end, start);
  }

  /**
   * Substitutes each {@code %s} in {@code template} with an argument. These are
   * matched by position: the first {@code %s} gets {@code args[0]}, etc. If there
   * are more arguments than placeholders, the unmatched arguments will be
   * appended to the end of the formatted message in square braces.
   *
   * @param template a non-null string containing 0 or more {@code %s}
   *                 placeholders.
   * @param args     the arguments to be substituted into the message template.
   *                 Arguments are converted to strings using
   *                 {@link String#valueOf(Object)}. Arguments can be null.
   * @return the string
   */
  // Note that this is somewhat-improperly used from Verify.java as well.
  static String format(String template, Object... args) {
    template = String.valueOf(template); // null -> "null"

    // start substituting the arguments into the '%s' placeholders
    StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
    int templateStart = 0;
    int i = 0;
    while (i < args.length) {
      int placeholderStart = template.indexOf("%s", templateStart);
      if (placeholderStart == -1) {
        break;
      }
      builder.append(template, templateStart, placeholderStart);
      builder.append(args[i++]);
      templateStart = placeholderStart + 2;
    }
    builder.append(template, templateStart, template.length());

    // if we run out of placeholders, append the extra args in square braces
    if (i < args.length) {
      builder.append(" [");
      builder.append(args[i++]);
      while (i < args.length) {
        builder.append(", ");
        builder.append(args[i++]);
      }
      builder.append(']');
    }

    return builder.toString();
  }

  // Preconditions}


  /**
   * Delete quietly.
   *
   * @param file the file
   * @return true, if successful
   */
  public static boolean deleteQuietly(File file) {
    if (file == null) {
      return false;
    }
    LogSupport.debug("deleteQuietly:{} exists:{}", file, file.exists());
    try {
      if (file.isDirectory()) {
        FileUtils.cleanDirectory(file);
      }
    } catch (Exception ignored) {
      LogSupport.error("{}", ExceptionUtils.getStackTrace(ignored));
    }
    try {
      return file.delete();
    } catch (Exception ignored) {
      LogSupport.error("{}", ExceptionUtils.getStackTrace(ignored));
      if (file.exists()) {
        try {
          FileUtils.forceDeleteOnExit(file);
          return true;
        } catch (IOException e) {
          LogSupport.error("{}", ExceptionUtils.getStackTrace(e));
          return false;
        }
      }
    }
    return false;
  }

  
  /**
   * To list.
   *
   * @param <Item> the generic type
   * @param items the items
   * @return the list
   */
  public static <Item> List<Item> toList(Item[] items) {
    if (items == null || items.length == 0) {
      return Collections.emptyList();
    }
    List<Item> list = new ArrayList<Item>(items.length);
    for (Item item : items) {
      list.add(item);
    }
    return list;
  }

  
  /**
   * To list.
   *
   * @param <Item> the generic type
   * @param items the items
   * @return the list
   */
  public static <Item> List<Item> toList(Iterator<Item> items) {
    List<Item> list = new ArrayList<Item>();
    while (items.hasNext()) {
      list.add(items.next());
    }
    return list;
  }

  /**
   * Split to list.
   *
   * @param toSplitString the to split string
   * @param separator the separator
   * @return the list
   */
  public static List<String> splitToList(String toSplitString, String separator) {
    return CommonSupport.toList(CommonSupport.split(toSplitString, separator));
  }

  
  /**
   * Gets the bean property quiet.
   *
   * @param bean the bean
   * @param property the property
   * @return the bean property quiet
   */
  public static Object getBeanPropertyQuiet(Object bean, String property) {
    try {
      return BeanUtil.pojo.getProperty(bean, property);
    } catch (Throwable e) {
      LogSupport.error("{}", ExceptionUtils.getStackTrace(e));
    }
    return null;
  }

  
  /**
   * Sets the bean property.
   *
   * @param bean the bean
   * @param property the property
   * @param value the value
   */
  public static void setBeanProperty(Object bean, String property, Object value) {
    BeanUtil.pojo.setProperty(bean, property, value);
  }

  
  /**
   * Gets the class instance quiet.
   *
   * @param <T> the generic type
   * @param cls the cls
   * @param args the args
   * @return the class instance quiet
   */
  public static <T> T getClassInstanceQuiet(final Class<T> cls, Object... args) {
    try {
      return ConstructorUtils.invokeConstructor(cls);
    } catch (Throwable e) {
      LogSupport.error("{}", ExceptionUtils.getStackTrace(e));
    }
    return null;
  }

  /**
   * Gets the stack trace.
   *
   * @param e the e
   * @return the stack trace
   */
  public static String getStackTrace(final Throwable e) {
    return ExceptionUtils.getStackTrace(e);
  }

  /**
   * Removes the null entry key or value.
   *
   * @param <K> the key type
   * @param <V> the value type
   * @param params the params
   * @return the map
   */
  public static <K, V> Map<K, V> removeNullEntryKeyOrValue(Map<K, V> params) {
    Map<K, V> result = new HashMap<K, V>();
    for (Iterator<Entry<K, V>> eachEntry = params.entrySet().iterator(); eachEntry.hasNext();) {
      Entry<K, V> entry = eachEntry.next();
      if (entry.getKey() != null && entry.getValue() != null) {
        result.put(entry.getKey(), entry.getValue());
      }
    }
    return result;
  }

  /**
   * Not implemented.
   *
   * @param message the message
   */
  public static void notImplemented(String message) {
    throw new NotImplementedException(message);
  }

  /**
   * Not implemented.
   */
  public static void notImplemented() {
    CommonSupport.notImplemented("NotImplemented");
  }

  /**
   * Trim left.
   *
   * @param input      the input
   * @param ignoreCase the ignore case
   * @param trimLefts  the trim lefts
   * @return the string
   */
  public static String trimLeft(String input, boolean ignoreCase, String... trimLefts) {
    String result = input;
    if (trimLefts == null) {
      trimLefts = new String[] { " " };
    }
    for (String trimLeft : trimLefts) {
      while (CommonSupport.startsWith(result, trimLeft, ignoreCase)) {
        result = CommonSupport.substringAfter(result, trimLeft);
      }
    }
    return result;
  }

  /**
   * Trim right.
   *
   * @param input      the input
   * @param ignoreCase the ignore case
   * @param trimRights the trim rights
   * @return the string
   */
  public static String trimRight(String input, boolean ignoreCase, String... trimRights) {
    String result = input;
    if (trimRights == null) {
      trimRights = new String[] { " " };
    }
    for (String trimRight : trimRights) {
      while (CommonSupport.endsWith(result, trimRight, ignoreCase)) {
        result = CommonSupport.substringBeforeLast(result, trimRight);
      }
    }
    return result;
  }

  /**
   * Trim x.
   *
   * @param input      the input
   * @param ignoreCase the ignore case
   * @param trimXs     the trim xs
   * @return the string
   */
  public static String trimX(String input, boolean ignoreCase, String... trimXs) {
    String result = input;
    if (trimXs == null) {
      trimXs = new String[] { " ", " ", "　" };
    }
    for (String trimX : trimXs) {
      while (CommonSupport.startsWith(result, trimX, ignoreCase)) {
        result = CommonSupport.substringAfter(result, trimX);
      }
      while (CommonSupport.endsWith(result, trimX, ignoreCase)) {
        result = CommonSupport.substringBeforeLast(result, trimX);
      }
    }
    return result;
  }

  /**
   * Trim x.
   *
   * @param input  the input
   * @param trimXs the trim xs
   * @return the string
   */
  public static String trimX(String input, String... trimXs) {
    return trimX(input, true, trimXs);
  }

  /**
   * Checks if is any null.
   *
   * @param items the items
   * @return true, if is any null
   */
  public static boolean isAnyNull(Object... items) {
    if (items != null) {
      for (Object obj : items) {
        if (obj == null) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Hiden.
   *
   * @param input     the input
   * @param hidenWith the hiden with
   * @param hidenFrom the hiden from
   * @param hidenTo   the hiden to
   * @return the string
   */
  public static String hiden(String input, String hidenWith, int hidenFrom, int hidenTo) {
    StringBuilder sb = new StringBuilder();
    sb.append(StringUtils.substring(input, 0, hidenFrom - 1));
    sb.append(StringUtils.repeat(hidenWith, hidenTo - hidenFrom + 1));
    sb.append(StringUtils.substring(input, hidenTo));
    return sb.toString();
  }

  /**
   * Message format.
   *
   * @param pattern the pattern
   * @param args    the args
   * @return the string
   */
  public static String messageFormat(String pattern, Object... args) {
    return MessageFormatter.arrayFormat(pattern, args).getMessage();
  }

  /**
   * Append.
   *
   * @param appendable the appendable
   * @param csq the csq
   */
  public static void append(Appendable appendable, CharSequence csq) {
    try {
      appendable.append(csq);
    } catch (Throwable e) {
      // ignore
    }
  }

  /**
   * Append line.
   *
   * @param appendable the appendable
   * @param csq the csq
   */
  public static void appendLine(Appendable appendable, CharSequence csq) {
    try {
      appendable.append(csq);
      appendable.append(StringUtils.LF);
    } catch (Throwable e) {
      // ignore
    }
  }

  /**
   * Append.
   *
   * @param appendable the appendable
   * @param pattern the pattern
   * @param args the args
   */
  public static void append(Appendable appendable, String pattern, Object... args) {
    try {
      appendable.append(messageFormat(pattern, args));
    } catch (Throwable e) {
      // ignore
    }
  }

  /**
   * Append line.
   *
   * @param appendable the appendable
   * @param pattern the pattern
   * @param args the args
   */
  public static void appendLine(Appendable appendable, String pattern, Object... args) {
    try {
      appendable.append(messageFormat(pattern, args));
      appendable.append(StringUtils.LF);
    } catch (Throwable e) {
      // ignore
    }
  }
}
