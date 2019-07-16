-- *****************************
-- module NumCbetw2
-- *****************************

module NumCbetw2 where

  import Data.Char 

  numcbetw2 :: Char -> Char -> Int
  numcbetw2 c1 c2  = if (c1 == c2) || (abs ((ord c1) - (ord c2))== 1) then 0 else
                     if (ord c1) > (ord c2) then 1 + numcbetw2 c1 (chr ((ord c2) + 1)) else 1 + numcbetw2 c1 (chr ((ord c2) - 1))
