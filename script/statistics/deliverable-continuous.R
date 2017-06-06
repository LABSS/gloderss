##
## Libraries
##
library(ggplot2)
library(data.table)

content <- c(1,2,3,4,5)
order <- c('P1','P2','P3','P4','P5')
fills <- c("white","white","white","white","white")
lines <- c(1,2,3,4,6)

base <- "/data/workspace/gloders/gloderss/output/data/Continuous"
baseOutput <- "/data/workspace/gloders/gloderss/output/figures/Continuous"

##
## Upload
##
replicas <- 0:9

simLen <- 50000
delta <- 100
con <- 200
ent <- 100

compensation <- NULL
consumer <- NULL
entrepreneur <- NULL
extortion <- NULL
io <- NULL
mafia <- NULL
mafiosi <- NULL
normative <- NULL
purchase <- NULL
state <- NULL

dir <- "S-before-1980-after-2000"
dirs <- 1:5
nReplicas <- length(replicas)
for(i in dirs){
  
  compensations <- NULL
  consumers <- NULL
  entrepreneurs <- NULL
  extortions <- NULL
  ios <- NULL
  mafias <- NULL
  mafiosis <- NULL
  normatives <- NULL
  purchases <- NULL
  states<- NULL
  
  for(replica in replicas){
    
    ##
    ## Compensation
    ##
    fname <- paste(base,"/",dir,"/",replica,"/compensation.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      compensations <- rbind(compensations, aux)
    }
    
    ##
    ## Consumer
    ##
    fname <- paste(base,"/",dir,"/",replica,"/consumer.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      consumers <- rbind(consumers, aux)
    }
    
    ##
    ## Entrepreneur
    ##
    fname <- paste(base,"/",dir,"/",replica,"/entrepreneur.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      entrepreneurs <- rbind(entrepreneurs, aux)
    }
    
    ##
    ## Extortion
    ##
    fname <- paste(base,"/",dir,"/",replica,"/extortion.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      extortions <- rbind(extortions, aux)
    }
    
    ##
    ## Intermediary Organization
    ##
    fname <- paste(base,"/",dir,"/",replica,"/intermediaryOrganization.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      ios <- rbind(ios, aux)
    }
    
    ##
    ## Mafia
    ##
    fname <- paste(base,"/",dir,"/",replica,"/mafia.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      mafias <- rbind(mafias, aux)
    }
    
    ##
    ## Mafiosi
    ##
    fname <- paste(base,"/",dir,"/",replica,"/mafiosi.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      mafiosis <- rbind(mafiosis, aux)
    }
    
    ##
    ## Normative
    ##
    fname <- paste(base,"/",dir,"/",replica,"/normative.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      normatives <- rbind(normatives, aux)
    }
    
    ##
    ## Purchase
    ##
    fname <- paste(base,"/",dir,"/",replica,"/purchase.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      purchases <- rbind(purchases, aux)
    }
    
    ##
    ## State
    ##
    fname <- paste(base,"/",dir,"/",replica,"/state.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      states <- rbind(states, aux)
    }
  }
  
  compensation[[i]] <- compensations
  consumer[[i]] <- consumers
  entrepreneur[[i]] <- entrepreneurs
  extortion[[i]] <- extortions
  io[[i]] <- ios
  mafia[[i]] <- mafias
  mafiosi[[i]] <- mafiosis
  normative[[i]] <- normatives
  purchase[[i]] <- purchases
  state[[i]] <- states  
}


##
## Number of Extortions
##
ne <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in content){
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

png(filename=paste0(baseOutput,"/numExt.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(numExt)),
                 fill=factor(scenario))) +
  xlab('Period') + ylab('Number of requests for pizzo made') + ylim(0,20000) +
  geom_boxplot(fill=fills) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.position = 'none',
        legend.text = element_text(colour="black", size=36, face="bold"),
        legend.title = element_text(colour="black", size=36, face="bold"))
dev.off()


##
## Hypothesis test
##
wilcox.test(ne[,1],ne[,2], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(ne[,2],ne[,3], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(ne[,3],ne[,4], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(ne[,4],ne[,5], paired=FALSE, alternative="two.sided")$p.value


##
## Number of Punishment
##
np <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in content){
  for(replica in replicas) {
    
    min <- ((i-1) * 10000)
    max <- i * 10000
    
    np[replica+1,i] <- nrow(subset(extortion[[1]], (paid == "false") &
                                     (mafiaPunished == "true") &
                                     (time >= min) &
                                     (time < max) &
                                     r == replica))
    
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], np[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("scenario","numPun"))

png(filename=paste0(baseOutput,"/numPun.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(numPun)),
                 fill=factor(scenario))) +
  xlab('Period') + ylab('Number of punishments') + ylim(0,6000) +
  geom_boxplot(fill=fills) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.position = 'none',
        legend.text = element_text(colour="black", size=36, face="bold"),
        legend.title = element_text(colour="black", size=36, face="bold"))
dev.off()


##
## Number of Mafiosi Imprisoned
##
data <- NULL
time <- seq(0,simLen-delta,delta)
for(i in content){
  dataM <- data.table(mafia[[1]])
  
  imp <- NULL
  for(t in time){
    imp <- rbind(imp, nrow(dataM[which(time == t & imprisoned == 'true'),]) / nReplicas)
  }
  
  data <- rbind(data, cbind(time, order[i], imp))
}

data <- data.table(data)
setnames(data, c("time", "V2", "V3"),
         c("time", "treatment", "imp"))

png(filename=paste0(baseOutput,"/numImprisoned.png"), width=1024, height=768)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(imp)),
                 group=treatment, linetype=as.factor(treatment))) +
  xlab('Time units') + ylab('Number of mafiosi imprisoned') +
  xlim(0,simLen) + ylim(0,20) +
  geom_line(size=1, colour="black") +
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
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.position = 'none',
        legend.text = element_text(colour="black", size=36, face="bold"),
        legend.title = element_text(colour="black", size=36, face="bold"))
dev.off()


##
## PROPORTION
##

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

png(filename=paste0(baseOutput,"/propPaidExt.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(paidExt))*100,
                 fill=factor(scenario))) +
  xlab('Period') + ylab('Proportion of paid pizzo') + ylim(65,85) +
  geom_boxplot(fill=fills) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.position = 'none',
        legend.title = element_text(colour="black", size=36, face="bold"),
        legend.text = element_text(colour="black", size=36, face="bold"))
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

png(filename=paste0(baseOutput,"/propDenExt.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario),
                 y=as.numeric(as.character(propDen))*100,
                 fill=factor(scenario))) +
  xlab('Period') + ylab('Proportion of pizzo requests reported') + ylim(0,25) +
  geom_boxplot(fill=fills) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.position = 'none',
        legend.title = element_text(colour="black", size=36, face="bold"),
        legend.text = element_text(colour="black", size=36, face="bold"))
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

png(filename=paste0(baseOutput,"/propImprisonment.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario),
                 y=as.numeric(as.character(propConv))*100,
                 fill=factor(scenario))) +
  xlab('Period') + ylab('Proportion of investigations\n leading to imprisonment') +
  ylim(0,10) +
  geom_boxplot(fill=fills) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.position = 'none',
        legend.title = element_text(colour="black", size=36, face="bold"),
        legend.text = element_text(colour="black", size=36, face="bold"))
dev.off()


##
## Hypothesis test
##
wilcox.test(pc[,1],pc[,2], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(pc[,2],pc[,3], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(pc[,3],pc[,4], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(pc[,4],pc[,5], paired=FALSE, alternative="two.sided")$p.value


##
## SALIENCE
##

##
## Pay Extortion
##
data <- NULL
time <- seq(0,simLen-delta,delta)
for(i in content){
  dataE <- data.table(entrepreneur[[1]])
  dataC <- data.table(consumer[[1]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    #sal <- c(dataE[which(time == t),]$saliencePayExtortion,
    #         dataC[which(time == t),]$saliencePayExtortion)
    
    sal <- c(dataE[which(time == t),]$saliencePayExtortion)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  data <- rbind(data, cbind(time, rep(order[i], length(mV)), mV, sV))
}
data <- data.table(data)
setnames(data, c("time", "V2", "mV", "sV"),
         c("time", "treatment", "mV", "sV"))

png(filename=paste0(baseOutput,"/salPayExt.png"), width=1024, height=768)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(mV)),
                 group=treatment)) +
  xlim(0,simLen) + ylim(0.2,0.7) +
  xlab('Time units') + ylab('Norm salience of \n \'Pay pizzo\' ') +
  geom_line(size=1, colour="black") +
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
        legend.position = 'none',
        legend.text = element_text(colour="black", size=36, face="bold"),
        legend.title = element_text(colour="black", size=36, face="bold"))
dev.off()


##
## Not Pay Extortion
##
data <- NULL
time <- seq(0,simLen-delta,delta)
for(i in content){
  dataE <- data.table(entrepreneur[[1]])
  dataC <- data.table(consumer[[1]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    #sal <- c(dataE[which(time == t),]$salienceNotPayExtortion,
    #         dataC[which(time == t),]$salienceNotPayExtortion)
    
    sal <- c(dataE[which(time == t),]$salienceNotPayExtortion)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  data <- rbind(data, cbind(time, rep(order[i], length(mV)), mV, sV))
}
data <- data.table(data)
setnames(data, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

png(filename=paste0(baseOutput,"/salNotPayExt.png"), width=1024, height=768)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(mV)),
                 group=treatment)) +
  xlim(0,simLen) + ylim(0.2,0.7) +
  xlab('Time units') + ylab('Norm salience of \n \'Do not pay pizzo\' ') +
  geom_line(size=1, colour="black") +
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
        legend.position = 'none',
        legend.text = element_text(colour="black", size=36, face="bold"),
        legend.title = element_text(colour="black", size=36, face="bold"))
dev.off()


##
## Denounce Extortion
##
data <- NULL
time <- seq(0,simLen,delta)
for(i in content){
  dataE <- data.table(entrepreneur[[1]])
  dataC <- data.table(consumer[[1]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    #sal <- c(dataE[which(time == t),]$salienceDenounce,
    #         dataC[which(time == t),]$salienceDenounce)
    
    sal <- c(dataE[which(time == t),]$salienceDenounce)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  data <- rbind(data, cbind(time, rep(order[i], length(mV)), mV, sV))
}
data <- data.table(data)
setnames(data, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

png(filename=paste0(baseOutput,"/salRep.png"), width=1024, height=768)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(mV)),
                 group=treatment)) +
  xlim(0,simLen) + ylim(0.2,0.7) +
  xlab('Time units') + ylab('Norm salience of \n \'Report\' ') +
  geom_line(size=1, colour="black") +
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
        legend.position = 'none',
        legend.text = element_text(colour="black", size=36, face="bold"),
        legend.title = element_text(colour="black", size=36, face="bold"))
dev.off()


##
## Not Denounce Extortion
##
data <- NULL
time <- seq(0,simLen,delta)
for(i in content){
  dataE <- data.table(entrepreneur[[1]])
  dataC <- data.table(consumer[[1]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    #sal <- c(dataE[which(time == t),]$salienceNotDenounce,
    #        dataC[which(time == t),]$salienceNotDenounce)
    
    sal <- c(dataE[which(time == t),]$salienceNotDenounce)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  data <- rbind(data, cbind(time, rep(order[i], length(mV)), mV, sV))
}
data <- data.table(data)
setnames(data, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

png(filename=paste0(baseOutput,"/salNotRep.png"), width=1024, height=768)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(mV)),
                 group=treatment)) +
  xlim(0,simLen) + ylim(0.2,0.7) +
  xlab('Time units') + ylab('Norm salience of \n \'Do not report\' ') +
  geom_line(size=1, colour="black") +
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
        legend.position = 'none',
        legend.text = element_text(colour="black", size=36, face="bold"),
        legend.title = element_text(colour="black", size=36, face="bold"))
dev.off()


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