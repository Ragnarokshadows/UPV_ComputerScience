-- *****************************
-- module Length
-- *****************************

module Length where

  length' [] = 0
  length' (x:t) = 1 + length' t