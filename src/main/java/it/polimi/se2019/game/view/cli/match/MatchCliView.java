package it.polimi.se2019.game.view.cli.match;

import it.polimi.se2019.utils.io.CliIO;
import it.polimi.se2019.utils.view.AbstractView;
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
    // Match doesn't render enything in this view
  }
}
