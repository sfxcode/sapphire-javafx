package com.sfxcode.sapphire.javafx.showcase.model

import io.circe.Decoder.Result
import io.circe.{ Decoder, Encoder, HCursor, Json }

import java.text.SimpleDateFormat
import java.util.Date

trait CirceSchema {
  val SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  implicit val DateFormat: Encoder[Date] with Decoder[Date] = new Encoder[Date] with Decoder[Date] {

    override def apply(a: Date): Json =
      Encoder.encodeString.apply("")

    override def apply(c: HCursor): Result[Date] = Decoder.decodeString.map(s => SimpleDateFormat.parse(s)).apply(c)
  }

}
