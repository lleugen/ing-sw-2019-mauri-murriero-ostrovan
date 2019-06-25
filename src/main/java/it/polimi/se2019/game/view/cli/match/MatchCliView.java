/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.game.view.cli.match;

import it.polimi.se2019.engine.io.CliIO;
import it.polimi.se2019.engine.view.AbstractView;
import it.polimi.se2019.game.model.match.MatchModel;

public class MatchCliView 
        extends AbstractView<MatchModel, CliIO> {
  /**
   * Initialize a new MatchCliView.
   * See description for this method in AbstractView
   */ 
  public MatchCliView(){
    super(MatchModel::decode);
  }

  /**
   * Render the view.
   * See description for this method in AbstractView
   *
   * @param data Model data to render
   * @param io   IOInterface to use for rendering data
   */
  @Override
  protected void render(MatchModel data, CliIO io) {
    io.markSection("MATCH");
  }
}
