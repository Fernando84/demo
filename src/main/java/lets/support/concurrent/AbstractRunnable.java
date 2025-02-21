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
package lets.support.concurrent;

import java.util.concurrent.atomic.AtomicLong;

/**
 * AbstractRunnable.
 *
 */
public abstract class AbstractRunnable implements RunnableWithId {

  /** The Constant counter. */
  private static final AtomicLong counter = new AtomicLong(0);

  /** The id. */
  private final String id;

  /**
   * Instantiates a new abstract runnable.
   *
   * @param id the id
   */
  public AbstractRunnable(String id) {
    super();
    this.id = id;
  }

  /** Instantiates a new abstract runnable. */
  public AbstractRunnable() {
    this(String.format("RunnableWithId(%s)", counter.incrementAndGet()));
  }

  /**
   * Id.
   *
   * @return the string
   */
  @Override
  public String id() {
    return this.id;
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  /**
   * Equals.
   *
   * @param obj the obj
   * @return true, if successful
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    AbstractRunnable other = (AbstractRunnable) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }
}
