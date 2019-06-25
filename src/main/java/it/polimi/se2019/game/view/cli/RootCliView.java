/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.game.view.cli;

import it.polimi.se2019.game.model.RootModel;
import it.polimi.se2019.engine.io.CliIO;
import it.polimi.se2019.engine.view.AbstractView;

public class RootCliView extends AbstractView<RootModel, CliIO> {
  public RootCliView(){
    super(RootModel::decode);
  }

  @Override
  protected void render(RootModel data, CliIO io) {
    // Root doesn't render enything in this view
  }
}
