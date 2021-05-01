import sbt.Keys._
import sbt._

name := """kpt-app"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "5.0.0",

  evolutions,
  // evolutionsのバージョン指定
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",

  // JDBC driverを指定（driverとは、playとmysqlを繋ぐためのアプリ）
  "mysql"             % "mysql-connector-java"  % "8.0.16",
)
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
