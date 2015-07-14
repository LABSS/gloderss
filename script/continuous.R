# S1 + S2 + S3 + S4 + S5
content <- c(1,2,3,4,5)
order <- c('P1','P2','P3','P4','P5')
shapes <- c(15,16,17,18,19)
sizes <- c(10,10,10,10,10)
fills <- c("white","white","white","white","white")
lines <- c(1,2,3,4,6)

dir <- c("S1-before-1980/0",
         "S2-1980-1990/0",
         "S3-1990-1995/0",
         "S4-1995-2000/0",
         "S5-after-2000/0")
dirs <- 1:5

##
## Number of Extortions
##
ne <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in dirs){
  for(replica in replicas) {
    
    min <- ((i-1) * 10000)
    max <- i * 10000
    
    ne[replica+1,i] <- nrow(subset(extortion[[1]], time >= min & time <= max &
                                     r == replica))
    
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], ne[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("scenario","numExt"))
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(numExt)),
                 fill=factor(scenario))) +
  xlab('Periods') + ylab('Number of Extortions') + ylim(0,20000) +
  geom_boxplot(fill=fills) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        #legend.text = element_text(colour="black", size=24, face="bold"))
        legend.position = 'none')

##
## Hypothesis test
##
wilcox.test(ne[,1],ne[,2], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(ne[,2],ne[,3], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(ne[,3],ne[,4], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(ne[,4],ne[,5], paired=FALSE, alternative="two.sided")$p.value


##
## Proportion of Paid Extortions
##
pe <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in dirs){
  for(replica in replicas) {
    
    min <- ((i-1) * 10000)
    max <- i * 10000
    
    pe[replica+1,i] <- nrow(subset(extortion[[1]], (time >= min & time <= max &
                                                      paid == "true" & r == replica))) /
      nrow(subset(extortion[[1]], time >= min & time <= max &
                    r == replica))
    
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], pe[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("scenario","paidExt"))
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(paidExt))*100,
                 fill=factor(scenario))) +
  xlab('Periods') + ylab('% of Paid Extortion') + ylim(65,85) +
  geom_boxplot(fill=fills) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        #legend.text = element_text(colour="black", size=24, face="bold"))
        legend.position = 'none')

##
## Hypothesis test
##
wilcox.test(pe[,1],pe[,2], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(pe[,2],pe[,3], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(pe[,3],pe[,4], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(pe[,4],pe[,5], paired=FALSE, alternative="two.sided")$p.value



##
## Proportion of Denunciation
##
pd <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in dirs){
  for(replica in replicas) {
    
    min <- ((i-1) * 10000)
    max <- i * 10000
    
    nDenExt <- nrow(subset(extortion[[1]], (time >= min & time <= max &
                                              paid == "false" &
                                              denouncedExtortion == "true" &
                                              r == replica)))
    
    nDenPun <- nrow(subset(extortion[[1]], (time >= min & time <= max &
                                              paid == "false" &
                                              denouncedPunishment == "true" &
                                              r == replica)))
    
    nExtortion <- nrow(subset(extortion[[1]], time >= min & time <= max &
                                r == replica))
    
    nNPaid <- nrow(subset(extortion[[1]], (time >= min & time <= max &
                                             paid == "false" & r == replica)))
    
    propDenExt <- nDenExt / nExtortion
    
    propDenPun <- nDenPun / nNPaid
    
    pd[replica+1,i] <-  propDenExt + propDenPun
    
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], pd[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("scenario","propDen"))
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(propDen))*100,
                 fill=factor(scenario))) +
  xlab('Periods') + ylab('% Denunciation') + ylim(0,25) +
  geom_boxplot(fill=fills) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        #legend.text = element_text(colour="black", size=24, face="bold"))
        legend.position = 'none')


##
## Hypothesis test
##
wilcox.test(pd[,1],pd[,2], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(pd[,2],pd[,3], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(pd[,3],pd[,4], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(pd[,4],pd[,5], paired=FALSE, alternative="two.sided")$p.value



##
## Proportion of Investigation Leading to Imprisonment
##
pc <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in dirs){
  for(replica in replicas) {
    
    min <- ((i-1) * 10000)
    max <- i * 10000
    
    nExtortion <- nrow(subset(extortion[[1]], time >= min & time <= max &
                                r == replica))
    
    nInvCon <- nrow(subset(extortion[[1]], (time >= min & time <= max &
                                              paid == "false" &
                                              (investigatedExtortion == "true" |
                                                 investigatedPunishment == "true") &
                                              mafiosoConvicted == "true" &
                                              r == replica)))
    
    pc[replica+1,i] <-  nInvCon / nExtortion
    
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], pc[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("scenario","propConv"))
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(propConv))*100,
                 fill=factor(scenario))) +
  xlab('Periods') + ylab('% Investigations Leading\n to Imprisonment') + ylim(0,10) +
  geom_boxplot(fill=fills) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        #legend.text = element_text(colour="black", size=24, face="bold"))
        legend.position = 'none')

##
## Hypothesis test
##
wilcox.test(pc[,1],pc[,2], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(pc[,2],pc[,3], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(pc[,3],pc[,4], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(pc[,4],pc[,5], paired=FALSE, alternative="two.sided")$p.value



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
                     group=treatment)) +
  xlim(0,simLen) + ylim(0.2,0.7) +
  xlab('Time Units') + ylab('\'Pay Extortion\'\n Norm Salience') +
  geom_line(size=1) +
  geom_vline(aes(xintercept=10000),
             color="black", linetype="dashed", size=1.5) +
  geom_vline(aes(xintercept=20000),
             color="black", linetype="dashed", size=1.5) +
  geom_vline(aes(xintercept=30000),
             color="black", linetype="dashed", size=1.5) +
  geom_vline(aes(xintercept=40000),
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
        legend.text = element_text(colour="black", size=36, face="bold"))


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
                     group=treatment)) +
  xlim(0,simLen) + ylim(0.2,0.7) +
  xlab('Time Units') + ylab('\'Do Not Pay Extortion\'\n Norm Salience') +
  geom_line(size=1) +
  geom_vline(aes(xintercept=10000),
             color="black", linetype="dashed", size=1.5) +
  geom_vline(aes(xintercept=20000),
             color="black", linetype="dashed", size=1.5) +
  geom_vline(aes(xintercept=30000),
             color="black", linetype="dashed", size=1.5) +
  geom_vline(aes(xintercept=40000),
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
        legend.text = element_text(colour="black", size=36, face="bold"))

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
                     group=treatment)) +
  xlim(0,simLen) + ylim(0.2,0.7) +
  xlab('Time Units') + ylab('\'Denounce Extortion\'\n Norm Salience') +
  geom_line(size=1) +
  geom_vline(aes(xintercept=10000),
             color="black", linetype="dashed", size=1.5) +
  geom_vline(aes(xintercept=20000),
             color="black", linetype="dashed", size=1.5) +
  geom_vline(aes(xintercept=30000),
             color="black", linetype="dashed", size=1.5) +
  geom_vline(aes(xintercept=40000),
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
        legend.text = element_text(colour="black", size=36, face="bold"))


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
                     group=treatment)) +
  xlim(0,simLen) + ylim(0.2,0.7) +
  xlab('Time Units') + ylab('\'Do Not Denounce Extortion\'\n Norm Salience') +
  geom_line(size=1) +
  geom_vline(aes(xintercept=10000),
             color="black", linetype="dashed", size=1.5) +
  geom_vline(aes(xintercept=20000),
             color="black", linetype="dashed", size=1.5) +
  geom_vline(aes(xintercept=30000),
             color="black", linetype="dashed", size=1.5) +
  geom_vline(aes(xintercept=40000),
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
        legend.text = element_text(colour="black", size=36, face="bold"))


nShift <- NULL
for(p in 1:5){
  sLen <- 10000 * p
  aux <- data.table(entrepreneur[[1]])
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
  nShift <- rbind(nShift, c(((numAgents-traditional)/numAgents), onlyNotPay/numAgents,
                            onlyDenounce/numAgents, (new/numAgents)))
}