#!/bin/sh
#PBS -l nodes=2:ppn=16,walltime=00:10:00
#PBS -W x="NACCESSPOLICY:SINGLEJOB"
#PBS -q cpa
#PBS -d .

mpiexec ./hello