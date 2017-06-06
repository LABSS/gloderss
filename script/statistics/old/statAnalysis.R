# S1 + S2
content <- c(1,2)
order <- c('S1','S2')
shapes <- c(15,16)
sizes <- c(10,10)
fills <- c("white","white")
lines <- c(1,2)

# S1 + S2 + S3
content <- c(1,49,61)
content <- c(1,2,3)
order <- c('S1','S2','S3')
shapes <- c(15,16,17)
sizes <- c(10,10,10)
fills <- c("white","white","white")
lines <- c(1,2,3)

# S2 + S3 + S4 + S5
content <- c(2,3,4,5)
order <- c('S2','S3','S4','S5')
shapes <- c(16,17,18,19)
sizes <- c(10,10,10,10)
fills <- c("white","white","white","white")
lines <- c(2,3,4,6)

# S1 + S2 + S3 + S4 + S5
content <- c(1,49,61,67,43)
content <- c(1,2,3,4,5)
order <- c('P1','P2','P3','P4','P5')
shapes <- c(15,16,17,18,19)
sizes <- c(10,10,10,10,10)
fills <- c("white","white","white","white","white")
lines <- c(1,2,3,4,6)

# S1s
content <- c(1,49,61,67)
content <- c(1,2,3,4)
order <- c('S1','S1-IO','S1-IO-State','S1-IO-State-Norm')
shapes <- c(15,16,17,18)
sizes <- c(10,10,10,10)
fills <- c("black","black","black","black")
lines <- c(1,2,3,4)

# Ss
content <- c(1,2,3,4,5,6,7,8,9,10,11,12,13)
order <- c("S1",
           "S1-IO",
           "S1-IO-Norm",
           "S1-Norm",
           "S2",
           "S2-IO",
           "S2-IO-Norm",
           "S3",
           "S3-IO-Weak",
           "S3-IO-Weak-Norm",
           "S3-Weak",
           "S4",
           "S5")
shapes <- c(15,16,17,18)
fills <- c("black","black","black","black","black","black","black","black",
           "black","black","black","black","black")

##
## Number of Extortions
##
ne <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in dirs){
  for(replica in replicas) {
  
    ne[replica+1,i] <- nrow(subset(extortion[[i]], r == replica))
    
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], ne[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("scenario","numExt"))
png(filename=paste0(base,"/numExt.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(numExt)),
                 fill=factor(scenario))) +
  xlab('Periods') + ylab('Number of Extortions') + ylim(0,35000) +
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
dev.off()

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
    
    pe[replica+1,i] <- nrow(subset(extortion[[i]], (paid == "true" & r == replica))) /
      nrow(subset(extortion[[i]], r == replica))
    
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], pe[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("scenario","paidExt"))
png(filename=paste0(base,"/propPaidExt.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(paidExt))*100,
                 fill=factor(scenario))) +
  xlab('Periods') + ylab('% of Paid Extortion') + ylim(55,80) +
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
dev.off()

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
    
    nDenExt <- nrow(subset(extortion[[i]], (paid == "false" &
                                              denouncedExtortion == "true" &
                                              r == replica)))
    
    nDenPun <- nrow(subset(extortion[[i]], (paid == "false" &
                                              denouncedPunishment == "true" &
                                              r == replica)))
    
    nExtortion <- nrow(subset(extortion[[i]], r == replica))
    
    nNPaid <- nrow(subset(extortion[[i]], (paid == "false" & r == replica)))
    
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
png(filename=paste0(base,"/propDenExt.png"), width=1024, height=768)
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
dev.off()

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
      
    nExtortion <- nrow(subset(extortion[[i]], r == replica))
    
    nInvCon <- nrow(subset(extortion[[i]], (paid == "false" &
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
png(filename=paste0(base,"/propImprisonment.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(propConv))*100,
                 fill=factor(scenario))) +
  xlab('Periods') + ylab('% Investigations Leading\n to Imprisonment') + ylim(0,8.5) +
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
dev.off()

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
png(filename=paste0(base,"/salPayExt.png"), width=1024, height=768)
ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0.2,0.7) +
  xlab('Time Units') + ylab('\'Pay Extortion\'\n Norm Salience') +
  geom_line(size=1) +
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
dev.off()

##
## Not Pay Extortion
##
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
png(filename=paste0(base,"/salNotPayExt.png"), width=1024, height=768)
ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0.2,0.7) +
  xlab('Time Units') + ylab('\'Do Not Pay Extortion\'\n Norm Salience') +
  geom_line(size=1) +
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
dev.off()

##
## Denounce Extortion
##
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
png(filename=paste0(base,"/salDenExt.png"), width=1024, height=768)
ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0.2,0.7) +
  xlab('Time Units') + ylab('\'Denounce Extortion\'\n Norm Salience') +
  geom_line(size=1) +
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
dev.off()

##
## Not Denounce Extortion
##
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
png(filename=paste0(base,"/salNotDenExt.png"), width=1024, height=768)
ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0.2,0.7) +
  xlab('Time Units') + ylab('\'Do Not Denounce Extortion\'\n Norm Salience') +
  geom_line(size=1) +
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
dev.off()

##
## Norms' set shift
##
nShift <- NULL
for(i in content){
  aux <- data.table(entrepreneur[[i]])
  new <- nrow(aux[which((time == simLen) &
                          (saliencePayExtortion <= salienceNotPayExtortion) &
                          (salienceDenounce >= salienceNotDenounce)),]) / nReplicas
  
  traditional <- nrow(aux[which((time == simLen) &
                                  (saliencePayExtortion > salienceNotPayExtortion) &
                                  (salienceDenounce < salienceNotDenounce)),]) / nReplicas
  
  onlyNotPay <- nrow(aux[which((time == simLen) &
                                 (saliencePayExtortion <= salienceNotPayExtortion) &
                                 (salienceDenounce < salienceNotDenounce)),]) / nReplicas
  
  onlyDenounce <- nrow(aux[which((time == simLen) &
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