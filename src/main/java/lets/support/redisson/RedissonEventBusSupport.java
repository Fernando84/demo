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
package lets.support.redisson;

import java.util.concurrent.atomic.AtomicReference;
import lets.support.InputOutputResultSupport;
import lets.support.ResultSupport;
import lets.support.event.DefaultEventBusSupport;
import org.redisson.api.RTopic;
import org.redisson.api.listener.MessageListener;
import org.redisson.api.listener.StatusListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class RedissonEventBusSupport.
 */
public class RedissonEventBusSupport extends DefaultEventBusSupport implements StatusListener, MessageListener<Object> {

 /** The Constant DEFAULT_TOPIC. */
 public final static String DEFAULT_TOPIC = "EventBus";

 /** The redisson. */
 private final RedissonSupport redisson;

 /** The topic. */
 private final String topic;

 /** The topic ref. */
 private AtomicReference<RTopic> topicRef = new AtomicReference<RTopic>();

 /** The logger. */
 private final Logger logger = LoggerFactory.getLogger(getClass());

 /**
  * Instantiates a new redisson event bus support.
  *
  * @param redisson the redisson
  * @param topic    the topic
  */
 public RedissonEventBusSupport(RedissonSupport redisson, String topic) {
  super();
  this.redisson = redisson;
  this.topic = topic;
  this.onStart();
 }

 /**
  * On start.
  *
  * @return the result support
  */
 public ResultSupport onStart() {
  ResultSupport result = new ResultSupport();
  try {
   InputOutputResultSupport<String, RTopic> getTopic = this.redisson.getTopic(this.topic);
   getTopic.checkState();
   RTopic rtopic = getTopic.getOutput();
   this.topicRef.set(rtopic);
   rtopic.addListener(this);
   rtopic.addListener(Object.class, this);
  } catch (Throwable e) {
   result.onException(e);
  }
  return result;
 }

 /**
  * Instantiates a new redisson event bus support.
  *
  * @param redisson the redisson
  */
 public RedissonEventBusSupport(RedissonSupport redisson) {
  this(redisson, DEFAULT_TOPIC);
 }

 /**
  * Post.
  *
  * @param event the event
  * @return the result support
  */
 @Override
 public ResultSupport post(Object event) {
  ResultSupport result = new ResultSupport();
  try {
   RTopic rtopic = this.topicRef.get();
   if (rtopic != null) {
    rtopic.publish(event);
   }
  } catch (Throwable e) {
   return super.post(event);
  }
  return result;
 }

 /**
  * On subscribe.
  *
  * @param channel the channel
  */
 @Override
 public void onSubscribe(String channel) {
  this.logger.info("onSubscribe: channel={}", channel);
 }

 /**
  * On unsubscribe.
  *
  * @param channel the channel
  */
 @Override
 public void onUnsubscribe(String channel) {
  this.logger.info("onUnsubscribe: channel={}", channel);
 }

 /**
  * On message.
  *
  * @param channel the channel
  * @param msg     the msg
  */
 @Override
 public void onMessage(CharSequence channel, Object msg) {
  this.logger.info("onMessage: channel={} msg={}", channel, msg);
  super.post(msg);
 }

}
