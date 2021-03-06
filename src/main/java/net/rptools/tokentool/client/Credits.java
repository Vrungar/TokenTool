/*
 * This software Copyright by the RPTools.net development team, and
 * licensed under the Affero GPL Version 3 or, at your option, any later
 * version.
 *
 * TokenTool Source Code is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU Affero General Public
 * License * along with this source Code.  If not, please visit
 * <http://www.gnu.org/licenses/> and specifically the Affero license
 * text at <http://www.gnu.org/licenses/agpl.html>.
 */
package net.rptools.tokentool.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.rptools.tokentool.AppConstants;
import net.rptools.tokentool.AppPreferences;
import net.rptools.tokentool.controller.TokenTool_Controller;
import net.rptools.tokentool.model.Window_Preferences;
import net.rptools.tokentool.util.I18N;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Credits {
  private static final Logger log = LogManager.getLogger(Credits.class);

  private Stage stage;

  public Credits(TokenTool_Controller tokenTool_Controller) {
    try {
      FXMLLoader fxmlLoader =
          new FXMLLoader(
              getClass().getResource(AppConstants.CREDITS_FXML),
              ResourceBundle.getBundle(AppConstants.TOKEN_TOOL_BUNDLE));
      Parent root = (Parent) fxmlLoader.load();

      stage = new Stage();
      Scene scene = new Scene(root);

      stage.getIcons().add(new Image(getClass().getResourceAsStream(AppConstants.TOKEN_TOOL_ICON)));
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.resizableProperty().setValue(false);
      stage.setTitle(I18N.getString("Credits.stage.title"));
      stage.setScene(scene);

      stage.setOnCloseRequest(
          new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
              AppPreferences.setPreference(
                  AppPreferences.WINDOW_CREDITS_PREFERENCES,
                  new Window_Preferences(stage).toJson());
              stage.hide();
            }
          });

      String preferencesJson =
          AppPreferences.getPreference(AppPreferences.WINDOW_CREDITS_PREFERENCES, null);
      if (preferencesJson != null) {
        Window_Preferences window_Preferences =
            new Gson().fromJson(preferencesJson, new TypeToken<Window_Preferences>() {}.getType());
        window_Preferences.setWindow(stage);
      }

      stage.show();
    } catch (Exception e) {
      log.error("Error loading Credits stage!", e);
    }
  }
}
