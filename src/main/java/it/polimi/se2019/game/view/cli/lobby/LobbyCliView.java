/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.game.view.cli.lobby;

import it.polimi.se2019.engine.io.CliIO;
import it.polimi.se2019.engine.view.AbstractView;
import it.polimi.se2019.game.model.lobby.LobbyModel;

public class LobbyCliView 
        extends AbstractView<LobbyModel, CliIO> {
  /**
   * Initialize a new LobbyCliView.
   * See description for this method in AbstractView
   */ 
  public LobbyCliView(){
    super(LobbyModel::decode);
  }

  /**
   * Render the view.
   * See description for this method in AbstractView
   *
   * @param data Model data to render
   * @param io   IOInterface to use for rendering data
   */
  @Override
  protected void render(LobbyModel data, CliIO io) {
    // Lobby doesn't render enything in this view
  }
}
