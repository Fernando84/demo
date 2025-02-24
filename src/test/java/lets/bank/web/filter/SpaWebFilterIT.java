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
package lets.bank.web.filter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lets.bank.IntegrationTest;
import lets.bank.security.AuthoritiesConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WithMockUser
@IntegrationTest
class SpaWebFilterIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFilterForwardsToIndex() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(forwardedUrl("/index.html"));
    }

    @Test
    void testFilterDoesNotForwardToIndexForApi() throws Exception {
        mockMvc.perform(get("/api/authenticate")).andExpect(status().isOk()).andExpect(forwardedUrl(null));
    }

    @Test
    @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
    void testFilterDoesNotForwardToIndexForV3ApiDocs() throws Exception {
        mockMvc.perform(get("/v3/api-docs")).andExpect(status().isOk()).andExpect(forwardedUrl(null));
    }

    @Test
    void testFilterDoesNotForwardToIndexForDotFile() throws Exception {
        mockMvc.perform(get("/file.js")).andExpect(status().isNotFound());
    }

    @Test
    void getBackendEndpoint() throws Exception {
        mockMvc.perform(get("/test")).andExpect(status().isOk()).andExpect(forwardedUrl("/index.html"));
    }

    @Test
    void forwardUnmappedFirstLevelMapping() throws Exception {
        mockMvc.perform(get("/first-level")).andExpect(status().isOk()).andExpect(forwardedUrl("/index.html"));
    }

    @Test
    void forwardUnmappedSecondLevelMapping() throws Exception {
        mockMvc.perform(get("/first-level/second-level")).andExpect(status().isOk()).andExpect(forwardedUrl("/index.html"));
    }

    @Test
    void forwardUnmappedThirdLevelMapping() throws Exception {
        mockMvc.perform(get("/first-level/second-level/third-level")).andExpect(status().isOk()).andExpect(forwardedUrl("/index.html"));
    }

    @Test
    void forwardUnmappedDeepMapping() throws Exception {
        mockMvc.perform(get("/1/2/3/4/5/6/7/8/9/10")).andExpect(forwardedUrl("/index.html"));
    }

    @Test
    void getUnmappedFirstLevelFile() throws Exception {
        mockMvc.perform(get("/foo.js")).andExpect(status().isNotFound());
    }

    /**
     * This test verifies that any files that aren't permitted by Spring Security will be forbidden.
     * If you want to change this to return isNotFound(), you need to add a request mapping that
     * allows this file in SecurityConfiguration.
     */
    @Test
    void getUnmappedSecondLevelFile() throws Exception {
        mockMvc.perform(get("/foo/bar.js")).andExpect(status().isForbidden());
    }

    @Test
    void getUnmappedThirdLevelFile() throws Exception {
        mockMvc.perform(get("/foo/another/bar.js")).andExpect(status().isForbidden());
    }
}
