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

import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

public class MysqlTestContainer implements SqlTestContainer {

    private static final Logger LOG = LoggerFactory.getLogger(MysqlTestContainer.class);

    private MySQLContainer<?> mysqlContainer;

    @Override
    public void destroy() {
        if (null != mysqlContainer && mysqlContainer.isRunning()) {
            mysqlContainer.stop();
        }
    }

    @Override
    public void afterPropertiesSet() {
        if (null == mysqlContainer) {
            mysqlContainer = new MySQLContainer<>("mysql:9.2.0")
                .withDatabaseName("LetsBank")
                .withTmpFs(Collections.singletonMap("/testtmpfs", "rw"))
                .withLogConsumer(new Slf4jLogConsumer(LOG))
                .withReuse(true);
        }
        if (!mysqlContainer.isRunning()) {
            mysqlContainer.start();
        }
    }

    @Override
    public JdbcDatabaseContainer<?> getTestContainer() {
        return mysqlContainer;
    }
}
