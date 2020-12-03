package com.sfxcode.sapphire.javafx.demo.issues.deltaspike

import com.sfxcode.sapphire.javafx.fxml.loader.BaseDocumentLoader
import javafx.fxml.FXMLLoader
import javax.enterprise.context.ApplicationScoped
import javax.inject.{Inject, Named}

@Named
@ApplicationScoped
class DeltaspikeDocumentLoader extends BaseDocumentLoader {

  @Inject
  var fxmlLoader: FXMLLoader = _

}
