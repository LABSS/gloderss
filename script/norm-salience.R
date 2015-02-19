library(rgl)

Wc <- 0.99
Wo <- 0.33
Wnpv <- -0.66
Wp <- 0.33
Ws <- 0.99
We <- 0.99

normSalience <- function(c, v, obsC, obsV, p, s, eC, eV) {
  
  nominator <- 0
  denominator <- 0
  
  # Own information
  own <- 0
  if ((c + v) > 0) {
    own <- (c - v) / (c + v)
    
    nominator <- nominator + 0.99
    denominator <- denominator + 1.98
  }
  
  # Observation information
  obs <- 0
  if ((obsC + obsV) > 0) {
    obs <- (obsC - obsV) / (obsC + obsV)
    
    nominator <- nominator + 0.33
    denominator <- denominator + 0.66
  }
  
  # Non-Punished Violation information
  npv <- 0
  if ((obsV + v) > 0) {
    npv <- max(0, (obsV + v) - p - s) / (obsV + v)
    
    nominator <- nominator + 0.66
    denominator <- denominator + 0.66
  }
  
  # Punishment and Sanction information
  pun <- 0
  san <- 0
  if (max(p + s, obsV + v) > 0) {
    if (p > 0) {
      pun = p / max(p, obsV + v)
      denominator <- denominator + 0.33
    }
    
    if (s > 0) {
      san = s / max(s, obsV + v)
      denominator <- denominator + 0.99
    }
  }
  
  # Norm Invocation information
  e <- 0
  if ((eC + eV) > 0) {
    e <- (eC - eV) / (eC + eV)
    
    nominator <- nominator + 0.99
    denominator <- denominator + 1.98
  }
  
  # Calculate Norm Salience
  salience <- 0
  if (denominator > 0) {
    salience <- (nominator + ((own * Wc) + (obs * Wo) + (npv * Wnpv) + 
                                (pun * Wp) + (san * Ws) + (e * We))) / denominator
  }
  
  return(salience)
}


#
# Test
#
x <- matrix(1:390625, nrow=1, ncol=390625)
i <- 1
for(c in seq(0,4,1)) {
  for(v in seq(0,4,1)) {
    for(obsC in seq(0,4,1)) {
      for(obsV in seq(0,4,1)) {
        for(p in seq(0,4,1)) {
          for(s in seq(0,4,1)) {
            for(eC in seq(0,4,1)) {
              for(eV in seq(0,4,1)) {
                salience <- normSalience(c,v,obsC,obsV,p,s,eC,eV)
                x[1,i] <- salience
                i = i + 1
                if((salience > 1.0000001) || (salience < 0)) {
                  print(c(salience,c,v,obsC,obsV,p,s,eC,eV))
                }
              }
            }
          }
        }
      }
    }
  }
}

plot3d(seq(0,4,1), seq(0,4,1), x[1,])

complier <- normSalience(10,1,10,1,0,2,10,1)
violator <- normSalience(1,10,1,10,0,0,1,10)
intermediary <- normSalience(10,10,10,10,0,10,10,10)
