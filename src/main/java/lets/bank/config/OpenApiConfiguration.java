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

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tech.jhipster.config.JHipsterConstants;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.apidoc.customizer.JHipsterOpenApiCustomizer;

@Configuration
@Profile(JHipsterConstants.SPRING_PROFILE_API_DOCS)
public class OpenApiConfiguration {

    public static final String API_FIRST_PACKAGE = "lets.bank.web.api";

    @Bean
    @ConditionalOnMissingBean(name = "apiFirstGroupedOpenAPI")
    public GroupedOpenApi apiFirstGroupedOpenAPI(
        JHipsterOpenApiCustomizer jhipsterOpenApiCustomizer,
        JHipsterProperties jHipsterProperties
    ) {
        JHipsterProperties.ApiDocs properties = jHipsterProperties.getApiDocs();
        return GroupedOpenApi.builder()
            .group("openapi")
            .addOpenApiCustomizer(jhipsterOpenApiCustomizer)
            .packagesToScan(API_FIRST_PACKAGE)
            .pathsToMatch(properties.getDefaultIncludePattern())
            .build();
    }
}
