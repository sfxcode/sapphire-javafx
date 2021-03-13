package com.sfxcode.sapphire.javafx.value

import com.sfxcode.sapphire.javafx.test.TestBean
import javafx.collections.ObservableList
import org.specs2.mutable.Specification

class BeanConversionsSpec extends Specification with SFXBeanConversions {

  "BeanConversions" should {

    "convert Bean To FXBean and back " in {
      val bean: SFXBean[TestBean] = TestBean()
      bean.getValue("name") must be equalTo "test"
      val convertedBean: TestBean = bean
      convertedBean.name must be equalTo "test"
    }

    "convert Bean List to ObservableList " in {
      val testBean = TestBean()
      val list     = List[TestBean](testBean)

      val observableList: ObservableList[SFXBean[TestBean]] = list

      observableList.head must be equalTo testBean

    }

  }

}
