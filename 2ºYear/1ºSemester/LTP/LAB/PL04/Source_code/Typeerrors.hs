-- *****************************
-- module Typeerrors
-- *****************************

module Typeerrors where

  convert :: (Char, Int) -> String
  convert (c,i) = [c] ++ show i

  -- main = convert (2,'c')
  main = convert ('c',2)