# S1 + S2
content <- c(1,2)
order <- c('P3','P4')
shapes <- c(15,16)
sizes <- c(10,10)
fills <- c("white","white")
lines <- c(1,2)

##
## Number of Extortion
##
data <- NULL
for(i in content){
  for(r in seq(0,simLen-delta,delta)){
    min <- r
    max <- r + delta
    data <- rbind(data, c(order[i], r, nrow(subset(extortion[[i]],
                                                   (time >= min) & (time < max))) / nReplicas))
  }
}

data <- data.frame(data)
setnames(data,c('X1','X2','X3'),c('scenario','time','numExt'))
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(numExt)),
                 linetype=scenario)) +
  xlab('Time Units') + ylab('Number of Extortions') + ylim(0,250) +
  geom_line(size=1) +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        #panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
        #legend.position = 'none')



##
## Proportion of Extortion
##
data <- NULL
for(i in content){
  for(r in seq(0,simLen-delta,delta)){
    min <- r
    max <- r + delta
    data <- rbind(data, c(order[i], r, nrow(subset(extortion[[i]],
                                                   (paid == "true") &
                                                   (time >= min) & (time < max))) /
                            nrow(subset(extortion[[i]],
                                        (time >= min) & (time < max)))))
  }
}

data <- data.frame(data)
setnames(data,c('X1','X2','X3'),c('scenario','time','paidExt'))
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(paidExt)),
                 linetype=scenario)) +
  xlab('Time Units') + ylab('% of Paid Extortion') + ylim(0,1) +
  geom_line(size=1) +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        #panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
        legend.position = 'none')



##
## Proportion of Denunciation
##
data <- NULL
for(i in content){
  for(r in seq(0,simLen-delta,delta)){
    min <- r
    max <- r + delta
    
    nDenExt <- nrow(subset(extortion[[i]], (paid == "false" &
                                              denouncedExtortion == "true" &
                                              time >= min & time < max)))
    
    nDenPun <- nrow(subset(extortion[[i]], (paid == "false" &
                                              denouncedPunishment == "true" &
                                              time >= min & time < max)))
    
    nExtortion <- nrow(subset(extortion[[i]], time >= min & time < max))
    
    nNPaid <- nrow(subset(extortion[[i]], (paid == "false" &
                                             time >= min & time < max)))
    
    propDenExt <- nDenExt / nExtortion
    
    propDenPun <- nDenPun / nNPaid
    
    data <- rbind(data, c(order[i], r, propDenExt + propDenPun))
  }
}

data <- data.frame(data)
setnames(data,c('X1','X2','X3'),c('scenario','time','propDen'))
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(propDen))*100,
                 linetype=scenario)) +
  xlab('Time Units') + ylab('% of Denounces') + ylim(0,50) +
  geom_line(size=1) +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        #panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))


##
## Proportion of Imprisonment
##
data <- NULL
for(i in content){
  for(r in seq(0,simLen-delta,delta)){
    min <- r
    max <- r + delta
    
    nExtortion <- nrow(subset(extortion[[i]], time >= min & time < max))
    
    nInvCon <- nrow(subset(extortion[[i]], (paid == "false" &
                                              (investigatedExtortion == "true" |
                                                 investigatedPunishment == "true") &
                                              mafiosoConvicted == "true" &
                                              time >= min & time < max)))
    
    data <- rbind(data, c(order[i], r, nInvCon / nExtortion))
  }
}

data <- data.frame(data)
setnames(data,c('X1','X2','X3'),c('scenario','time','propConv'))
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(propConv))*100,
                 linetype=scenario)) +
  xlab('Time Units') + ylab('% Investigations Leading\n to Imprisonment') + ylim(0,15) +
  geom_line(size=1) +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        #panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))


##
## Pay Extortion
##
data <- NULL
salience <- NULL
time <- seq(0,simLen,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  dataC <- data.table(consumer[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    #sal <- c(dataE[which(time == t),]$saliencePayExtortion,
    #         dataC[which(time == t),]$saliencePayExtortion)
    
    sal <- c(dataE[which(time == t),]$saliencePayExtortion)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  salience <- rbind(salience, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
salience <- data.table(salience)
setnames(salience, c("time", "V2", "mV", "sV"),
         c("time", "treatment", "mV", "sV"))

ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0.25,0.65) +
  xlab('Time Units') + ylab('\'Pay Extortion\'\n Norm Salience') +
  geom_line(size=1) +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))


##
## Not Pay Extortion
##
data <- NULL
salience <- NULL
time <- seq(0,simLen,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  dataC <- data.table(consumer[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    #sal <- c(dataE[which(time == t),]$salienceNotPayExtortion,
    #         dataC[which(time == t),]$salienceNotPayExtortion)
    
    sal <- c(dataE[which(time == t),]$salienceNotPayExtortion)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  salience <- rbind(salience, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
salience <- data.table(salience)
setnames(salience, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0.2,0.7) +
  xlab('Time Units') + ylab('\'Do Not Pay Extortion\'\n Norm Salience') +
  geom_line(size=1) +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))

##
## Denounce Extortion
##
data <- NULL
salience <- NULL
time <- seq(0,simLen,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  dataC <- data.table(consumer[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    #sal <- c(dataE[which(time == t),]$salienceDenounce,
    #         dataC[which(time == t),]$salienceDenounce)
    
    sal <- c(dataE[which(time == t),]$salienceDenounce)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  salience <- rbind(salience, cbind(time, rep(order[p], length(mV)), mV, sV))
  p <- p + 1
}
salience <- data.table(salience)
setnames(salience, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0.2,0.7) +
  xlab('Time Units') + ylab('\'Denounce Extortion\'\n Norm Salience') +
  geom_line(size=1) +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))


##
## Not Denounce Extortion
##
data <- NULL
salience <- NULL
time <- seq(0,simLen,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  dataC <- data.table(consumer[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    #sal <- c(dataE[which(time == t),]$salienceNotDenounce,
    #        dataC[which(time == t),]$salienceNotDenounce)
    
    sal <- c(dataE[which(time == t),]$salienceNotDenounce)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  salience <- rbind(salience, cbind(time, rep(order[p], length(mV)), mV, sV))
  p <- p + 1
}
salience <- data.table(salience)
setnames(salience, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0.2,0.7) +
  xlab('Time Units') + ylab('\'Do Not Denounce Extortion\'\n Norm Salience') +
  geom_line(size=1) +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))


##
## Norms' set shift
##
nShift <- NULL
for(p in 1:2){
  sLen <- 10000 * p
for(i in content){
  aux <- data.table(entrepreneur[[i]])
  new <- nrow(aux[which((time == sLen) &
                          (saliencePayExtortion <= salienceNotPayExtortion) &
                          (salienceDenounce >= salienceNotDenounce)),]) / nReplicas
  
  traditional <- nrow(aux[which((time == sLen) &
                                  (saliencePayExtortion > salienceNotPayExtortion) &
                                  (salienceDenounce < salienceNotDenounce)),]) / nReplicas
  
  onlyNotPay <- nrow(aux[which((time == sLen) &
                                 (saliencePayExtortion <= salienceNotPayExtortion) &
                                 (salienceDenounce < salienceNotDenounce)),]) / nReplicas
  
  onlyDenounce <- nrow(aux[which((time == sLen) &
                                   (saliencePayExtortion > salienceNotPayExtortion) &
                                   (salienceDenounce >= salienceNotDenounce)),]) / nReplicas
  
  #aux <- data.table(consumer[[i]])
  #new <- new + nrow(aux[which((time == simLen) &
  #                              (saliencePayExtortion <= salienceNotPayExtortion) &
  #                              (salienceDenounce >= salienceNotDenounce)),]) / nReplicas
  
  #traditional <- traditional + nrow(aux[which((time == simLen) &
  #                                              (saliencePayExtortion > salienceNotPayExtortion) &
  #                                              (salienceDenounce < salienceNotDenounce)),]) / nReplicas
  
  #onlyNotPay <- onlyNotPay + nrow(aux[which((time == simLen) &
  #                                            (saliencePayExtortion <= salienceNotPayExtortion) &
  #                                            (salienceDenounce < salienceNotDenounce)),]) / nReplicas
  
  #onlyDenounce <- onlyDenounce + nrow(aux[which((time == simLen) &
  #                                                (saliencePayExtortion > salienceNotPayExtortion) &
  #                                                (salienceDenounce >= salienceNotDenounce)),]) / nReplicas
  
  numAgents <- ent #+ con
  nShift <- rbind(nShift, c(i, ((numAgents-traditional)/numAgents), onlyNotPay/numAgents,
                            onlyDenounce/numAgents, (new/numAgents)))
}
}