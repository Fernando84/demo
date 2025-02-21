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
package lets.support.event;

import lets.support.EventBusService;
import lets.support.ResultSupport;
import org.greenrobot.eventbus.EventBus;

/**
 * The Class DefaultEventBusSupport.
 */
public class DefaultEventBusSupport implements EventBusService {

 /**
  * Subscribe.
  *
  * @param subscriber the subscriber
  * @return the result support
  */
 public ResultSupport subscribe(Object subscriber) {
  ResultSupport result = new ResultSupport();
  try {
   EventBus.getDefault().register(subscriber);
  } catch (Throwable e) {
   // result.onException(e);
  }
  return result;
 }

 /**
  * Unsubscribe.
  *
  * @param subscriber the subscriber
  * @return the result support
  */
 public ResultSupport unsubscribe(Object subscriber) {
  ResultSupport result = new ResultSupport();
  try {
   EventBus.getDefault().unregister(subscriber);
  } catch (Throwable e) {
   // result.onException(e);
  }
  return result;
 }

 /**
  * Post.
  *
  * @param event the event
  * @return the result support
  */
 public ResultSupport post(Object event) {
  ResultSupport result = new ResultSupport();
  try {
   EventBus.getDefault().post(event);
  } catch (Throwable e) {
   result.onException(e);
  }
  return result;
 }

}
