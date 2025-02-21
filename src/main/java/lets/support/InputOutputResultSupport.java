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


/**
 * InputOutputResultSupport.
 *
 * @param <Input> the generic type
 * @param <Outout> the generic type
 */
public class InputOutputResultSupport<Input, Outout> extends ResultSupport {

 /** The Constant serialVersionUID. */
 private static final long serialVersionUID = 1L;
 
 /** The input. */
 private Input input;
 
 /** The output. */
 private Outout output;

 /**
  * Gets the input.
  *
  * @return the input
  */
 public Input getInput() {
  return input;
 }

 /**
  * Sets the input.
  *
  * @param input the new input
  */
 public void setInput(Input input) {
  this.input = input;
 }

 /**
  * Gets the output.
  *
  * @return the output
  */
 public Outout getOutput() {
  return output;
 }

 /**
  * Sets the output.
  *
  * @param output the output
  */
 public void setOutput(Outout output) {
  this.output = output;
 }

}
