-- *****************************
-- module Nextchar
-- *****************************

module Nextchar where

  import Data.Char 

  nextchar :: Char -> Char

  -- nextchar c = chr ((ord c) + 1)

  nextchar c
          | c == 'z' = 'a'
          | c == 'Z' = 'A'
          | otherwise = chr ((ord c) + 1)