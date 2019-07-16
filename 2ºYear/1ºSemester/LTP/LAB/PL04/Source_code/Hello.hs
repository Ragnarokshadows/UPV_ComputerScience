-- *****************************
-- module Hello
-- *****************************

module Hello where
  
  -- right hello func:
  hello n = concat (replicate n "hello ")
  hello' n = putStrLn (concat (replicate n "hello \n"))