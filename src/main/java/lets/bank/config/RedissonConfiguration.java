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

import java.net.URI;
import lets.support.concurrent.locks.LockService;
import lets.support.redisson.RedissonLockService;
import lets.support.redisson.RedissonSupport;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.config.JHipsterProperties;

@Configuration
public class RedissonConfiguration {

  @Bean
  public Config config(JHipsterProperties jHipsterProperties) {
    URI redisUri = URI.create(jHipsterProperties.getCache().getRedis().getServer()[0]);
    Config config = new Config();
    config.setCodec(new org.redisson.codec.SerializationCodec());
    if (jHipsterProperties.getCache().getRedis().isCluster()) {
      ClusterServersConfig clusterServersConfig = config.useClusterServers()
          .setMasterConnectionPoolSize(
              jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
          .setMasterConnectionMinimumIdleSize(
              jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
          .setSubscriptionConnectionPoolSize(
              jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
          .addNodeAddress(jHipsterProperties.getCache().getRedis().getServer());

      if (redisUri.getUserInfo() != null) {
        clusterServersConfig
            .setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
      }
    } else {
      SingleServerConfig singleServerConfig = config.useSingleServer()
          .setConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
          .setConnectionMinimumIdleSize(
              jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
          .setSubscriptionConnectionPoolSize(
              jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
          .setAddress(jHipsterProperties.getCache().getRedis().getServer()[0]);

      if (redisUri.getUserInfo() != null) {
        singleServerConfig
            .setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
      }
    }
    return config;
  }

  @Bean
  RedissonSupport redissonSupport(Config config) {
    RedissonSupport rs = new RedissonSupport().withConfig(config);
    return rs;
  }

  @Bean
  LockService lockService(RedissonSupport rs) {
    return new RedissonLockService(rs);
  }
}
