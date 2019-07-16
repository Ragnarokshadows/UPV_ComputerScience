-- *****************************
-- module DaysAmonth
-- *****************************

module DaysAmonth where
   
  leapyear :: Int -> Bool
  leapyear i = if mod i 4 == 0 && mod i 100 == 0 then (mod i 400 == 0) else 
               if mod i 4 == 0 && mod i 100 /= 0 then True else False
  
  daysAmonth :: Int -> Int -> Int
  daysAmonth m y = if m == 2 
                      then if (leapyear y) then 29 else 28
                      else 
                   if m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12 then 31 else 30