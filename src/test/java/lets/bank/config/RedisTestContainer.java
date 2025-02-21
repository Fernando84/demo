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
package lets.bank.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

public class RedisTestContainer implements InitializingBean, DisposableBean {

    private GenericContainer redisContainer;
    private static final Logger LOG = LoggerFactory.getLogger(RedisTestContainer.class);

    @Override
    public void destroy() {
        if (null != redisContainer && redisContainer.isRunning()) {
            redisContainer.close();
        }
    }

    @Override
    public void afterPropertiesSet() {
        if (null == redisContainer) {
            redisContainer = new GenericContainer("redis:7.4.2")
                .withExposedPorts(6379)
                .withLogConsumer(new Slf4jLogConsumer(LOG))
                .withReuse(true);
        }
        if (!redisContainer.isRunning()) {
            redisContainer.start();
        }
    }

    public GenericContainer getRedisContainer() {
        return redisContainer;
    }
}
