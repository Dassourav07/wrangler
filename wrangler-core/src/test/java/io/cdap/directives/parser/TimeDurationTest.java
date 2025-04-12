/*
 * Copyright © 2025 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */

 package io.cdap.directives.parser;

 import com.google.gson.JsonElement;
 import com.google.gson.JsonPrimitive;
 import io.cdap.wrangler.api.parser.Token;
 import io.cdap.wrangler.api.parser.TokenType;
 
 /**
  * Token implementation for representing time durations like "5s", "10m", etc.
  */
 public class TimeDurationTest implements Token {
   private final String value;
   private final long milliseconds;
 
   public TimeDurationTest(String value) {
     this.value = value;
     this.milliseconds = parseToMillis(value);
   }
 
   private long parseToMillis(String input) {
     input = input.toLowerCase().trim();
     double number = Double.parseDouble(input.replaceAll("[^0-9.]", ""));
 
     if (input.endsWith("ms")) {
       return (long) number;
     }
     if (input.endsWith("s")) {
       return (long) (number * 1000);
     }
     if (input.endsWith("m")) {
       return (long) (number * 60 * 1000);
     }
     if (input.endsWith("h")) {
       return (long) (number * 60 * 60 * 1000);
     }
     if (input.endsWith("d")) {
       return (long) (number * 24 * 60 * 60 * 1000);
     }
     return (long) number;
   }
 
   public long getMilliseconds() {
     return milliseconds;
   }
 
   @Override
   public String value() {
     return value;
   }
 
   @Override
   public TokenType type() {
     return TokenType.TIMEDURATION;
   }
 
   @Override
   public JsonElement toJson() {
     return new JsonPrimitive(value);
   }
}



