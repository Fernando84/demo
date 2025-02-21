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

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.function.BiFunction;
import lets.support.DataResultSupport;
import lets.support.InputOutputResultSupport;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RMapCache;
import org.redisson.api.RPatternTopic;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ConfigSupport;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Class RedissonSupport.
 */
public class RedissonSupport {

 /** The logger. */
 private final Logger logger = LoggerFactory.getLogger(getClass());
 /** The config. */
 private Config config;

 /** The config support. */
 private ConfigSupport configSupport = new ConfigSupport();
 /** The redission. */
 private AtomicReference<RedissonClient> redission = new AtomicReference<RedissonClient>();


 /**
  * Gets the config.
  *
  * @return the config
  */
 public synchronized Config getConfig() {
  if (this.config == null) {
   this.config = this.onBuildConfigDefault();
  }
  return config;
 }


 /**
  * On build config default.
  *
  * @return the config
  */
 public Config onBuildConfigDefault() {
  this.withSingleServerConfigDefault();
  return this.config;
 }

 /**
  * On build redisson client.
  *
  * @return the redisson client
  */
 public RedissonClient onBuildRedissonClient() {
  RedissonClient redisson = Redisson.create(this.getConfig());
  return redisson;
 }


 /**
  * Did build redisson client.
  *
  * @param client the client
  */
 public void didBuildRedissonClient(RedissonClient client) {
  this.logger.info("didBuildRedissonClient: config={}", this.writeConfigToJson(this.config));
 }

 /**
  * Call.
  *
  * @param <TInput>  the generic type
  * @param <TOutput> the generic type
  * @param input     the input
  * @param func      the func
  * @return the input output result support
  */
 public <TInput, TOutput> InputOutputResultSupport<TInput, TOutput> call(TInput input, BiFunction<RedissonClient, TInput, TOutput> func) {
  InputOutputResultSupport<TInput, TOutput> result = new InputOutputResultSupport<TInput, TOutput>();
  try {
   DataResultSupport<RedissonClient> client = this.getOrBuildRedissonClient();
   client.checkState();
   result.setInput(input);
   result.setOutput(func.apply(client.getData(), input));
  } catch (Throwable e) {
   result.onException(e);
  }
  return result;
 }

 /**
  * Do with redisson.
  *
  * @param <T>      the generic type
  * @param callback the callback
  * @return the data result support
  */
 public <T> DataResultSupport<T> doWithRedisson(RedissonCallback<T> callback) {
  DataResultSupport<T> result = new DataResultSupport<T>();
  try {
   DataResultSupport<RedissonClient> client = this.getOrBuildRedissonClient();
   client.checkState();
   result.setData(callback.doWithRedisson(client.getData()));
  } catch (Throwable e) {
   result.onException(e);
  }
  return result;
 }

 /**
  * Gets the atomic long.
  *
  * @param name the name
  * @return the atomic long
  */
 public InputOutputResultSupport<String, RAtomicLong> getAtomicLong(String name) {
  return this.call(name, (t, u) -> t.getAtomicLong(u));
 }

 /**
  * Gets the deque.
  *
  * @param <V>  the value type
  * @param name the name
  * @return the deque
  */
 public <V> InputOutputResultSupport<String, Deque<V>> getDeque(String name) {
  return this.call(name, (t, u) -> t.getDeque(u));
 }

 /**
  * Gets the executor service.
  *
  * @param name the name
  * @return the executor service
  */
 public InputOutputResultSupport<String, ExecutorService> getExecutorService(String name) {
  return this.call(name, (t, u) -> t.getExecutorService(u));
 }

 /**
  * Gets the list.
  *
  * @param <V>  the value type
  * @param name the name
  * @return the list
  */
 public <V> InputOutputResultSupport<String, List<V>> getList(String name) {
  return this.call(name, (t, u) -> t.getList(u));
 }

 /**
  * Gets the lock.
  *
  * @param name the name
  * @return the lock
  */
 public InputOutputResultSupport<String, Lock> getLock(String name) {
  return this.call(name, (t, u) -> t.getLock(u));
 }

 /**
  * Gets the map.
  *
  * @param <K>  the key type
  * @param <V>  the value type
  * @param name the name
  * @return the map
  */
 public <K, V> InputOutputResultSupport<String, Map<K, V>> getMap(String name) {
  return this.call(name, (t, u) -> t.getMap(u));
 }

 /**
  * Gets the map cache.
  *
  * @param <K>  the key type
  * @param <V>  the value type
  * @param name the name
  * @return the map cache
  */
 public <K, V> InputOutputResultSupport<String, RMapCache<K, V>> getMapCache(String name) {
  return this.call(name, (t, u) -> t.getMapCache(u));
 }

 /**
  * Gets the or build redisson client.
  *
  * @return the or build redisson client
  */
 DataResultSupport<RedissonClient> getOrBuildRedissonClient() {
  DataResultSupport<RedissonClient> result = new DataResultSupport<RedissonClient>();
  try {
   if (this.redission.get() == null) {
    RedissonClient client = this.onBuildRedissonClient();
    this.redission.set(client);
    this.didBuildRedissonClient(client);
   }
   result.setData(this.redission.get());
  } catch (Throwable e) {
   result.onException(e);
  }
  return result;
 }


 /**
  * With redisson client.
  *
  * @param client the client
  * @return the redisson support
  */
 public RedissonSupport withRedissonClient(RedissonClient client) {
  this.redission.set(client);
  return this;
 }

 /**
  * Gets the queue.
  *
  * @param <V>  the value type
  * @param name the name
  * @return the queue
  */
 public <V> InputOutputResultSupport<String, Queue<V>> getQueue(String name) {
  return this.call(name, (t, u) -> t.getQueue(u));
 }

 /**
  * Gets the rate limiter.
  *
  * @param name the name
  * @return the rate limiter
  */
 public InputOutputResultSupport<String, RRateLimiter> getRateLimiter(String name) {
  return this.call(name, (t, u) -> t.getRateLimiter(u));
 }

 /**
  * Gets the topic.
  *
  * @param name the name
  * @return the topic
  */
 public InputOutputResultSupport<String, RTopic> getTopic(String name) {
  return this.call(name, (t, u) -> t.getTopic(u));
 }

 /**
  * Gets the pattern topic.
  *
  * @param name the name
  * @return the pattern topic
  */
 public InputOutputResultSupport<String, RPatternTopic> getPatternTopic(String name) {
  return this.call(name, (t, u) -> t.getPatternTopic(u));
 }


 /**
  * On customize.
  *
  * @param config the config
  */
 public void onCustomize(SingleServerConfig config) {
 }


 /**
  * With config.
  *
  * @param config the config
  * @return the redisson support
  */
 public RedissonSupport withConfig(Config config) {
  this.config = new Config(config);
  this.redission.set(null);
  return this;
 }


 /**
  * With config.
  *
  * @return the redisson support
  */
 public RedissonSupport withConfig() {
  return this.withConfig(new Config());
 }

 /**
  * With single server config.
  *
  * @param host     the host
  * @param port     the port
  * @param database the database
  * @param password the password
  * @return the redisson support
  */
 public RedissonSupport withSingleServerConfig(String host, int port, int database, String password) {
  this.withConfig();
  this.logger.info("Redisson: withSingleServerConfig: host={} port={} database={} password={}", host, port, database, password);
  SingleServerConfig singleServer = this.config.useSingleServer();
  singleServer.setAddress(String.format("redis://%s:%s", host, port));
  if (StringUtils.isNotEmpty(password)) {
   singleServer.setPassword(password);
  }
  singleServer.setDatabase(database);
  this.onCustomize(singleServer);
  return this;
 }

 /**
  * With single server config default.
  *
  * @return the redisson support
  */
 public RedissonSupport withSingleServerConfigDefault() {
  return this.withSingleServerConfig("127.0.0.1", 6379, 1, "");
 }

 /**
  * Write config to json.
  *
  * @param config the config
  * @return the data result support
  */
 public DataResultSupport<String> writeConfigToJson(Config config) {
  DataResultSupport<String> result = new DataResultSupport<String>();
  try {
   result.setData(this.configSupport.toJSON(config));
  } catch (Throwable e) {
   result.onException(e);
  }
  return result;
 }

 /**
  * Read config from json.
  *
  * @param json the json
  * @return the data result support
  */
 public DataResultSupport<Config> readConfigFromJson(String json) {
  DataResultSupport<Config> result = new DataResultSupport<Config>();
  try {
   result.setData(this.configSupport.fromJSON(json, Config.class));
  } catch (Throwable e) {
   result.onException(e);
  }
  return result;
 }

}
