##
## Libraries
##
library(ggplot2)
library(data.table)


##
##
##
base <- "/data/workspace/gloders/gloderss/output/data/tus-100000"
baseOutput <- "/data/workspace/gloders/gloderss/output/figures/tus-100000"

dir <- c("iw05-nw05-weak-noNorm-violent-low-inactive",
         "iw05-nw05-weak-noNorm-hidden-low-inactive")

content <- c(1, 2)
order <- c("violent", "hidden")
fills <- c("white", "white")
lines <- c(1, 2)


##
## Upload
##
replicas <- 0:9

simLen <- 100000
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

dirs <- 1:length(dir)
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
    fname <- paste(base,"/",dir[i],"/",replica,"/compensation.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      compensations <- rbind(compensations, aux)
    }
    
    ##
    ## Consumer
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/consumer.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      consumers <- rbind(consumers, aux)
    }
    
    ##
    ## Entrepreneur
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/entrepreneur.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      entrepreneurs <- rbind(entrepreneurs, aux)
    }
    
    ##
    ## Extortion
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/extortion.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      extortions <- rbind(extortions, aux)
    }
    
    ##
    ## Intermediary Organization
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/intermediaryOrganization.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      ios <- rbind(ios, aux)
    }
    
    ##
    ## Mafia
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/mafia.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      mafias <- rbind(mafias, aux)
    }
    
    ##
    ## Mafiosi
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/mafiosi.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      mafiosis <- rbind(mafiosis, aux)
    }
    
    ##
    ## Normative
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/normative.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      normatives <- rbind(normatives, aux)
    }
    
    ##
    ## Purchase
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/purchase.csv", sep="")
    if (file.info(fname)$size > 0) {
      aux <- data.table(read.csv(fname, header=TRUE, sep=";"))[which(time <= simLen),]
      aux[,r:=replica]
      purchases <- rbind(purchases, aux)
    }
    
    ##
    ## State
    ##
    fname <- paste(base,"/",dir[i],"/",replica,"/state.csv", sep="")
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
## NUMBER
##

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
setnames(data,c("X1","X2"),c("scenario","nExt"))

png(filename=paste0(baseOutput,"/numExt.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario),
                 y=as.numeric(as.character(nExt)),
                 fill=factor(scenario))) +
  xlab('Configuration') + ylab('Number of requests for pizzo made') +
  ylim(0,350000) +
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
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_blank())
dev.off()


##
## Number of Paid of Extortion
##
npe <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in dirs){
  for(replica in replicas) {
    
    npe[replica+1,i] <- nrow(subset(extortion[[i]], (paid == "true" & r == replica)))
    
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], npe[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("scenario","nPaid"))

png(filename=paste0(baseOutput,"/numPaidExt.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario),
                 y=as.numeric(as.character(nPaid)),
                 fill=factor(scenario))) +
  xlab('Configuration') + ylab('Number of pizzo payments made') +
  ylim(0,350000) +
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
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_blank())
dev.off()


##
## Number of Denounces
##
nr <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in dirs){
  for(replica in replicas) {
    
    nr[replica+1,i] <- nrow(subset(extortion[[i]], paid == "false" &
                                     (denouncedExtortion == "true" |
                                        denouncedPunishment == "true") &
                                     r == replica))
    
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], nr[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("scenario","nRep"))

png(filename=paste0(baseOutput,"/numReported.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario),
                 y=as.numeric(as.character(nRep)),
                 fill=factor(scenario))) +
  xlab('Configuration') + ylab('Number of pizzo requests reported') +
  ylim(0,5000) +
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
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_blank())
dev.off()


##
## Number of Punishment
##
np <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in dirs){
  for(replica in replicas) {
    
    np[replica+1,i] <- nrow(subset(extortion[[i]], (paid == "false" &
                                                      mafiaPunished == "true" &
                                                      r == replica)))
    
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], np[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("Configuration","nPun"))

png(filename=paste0(baseOutput,"/numPun.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario),
                 y=as.numeric(as.character(nPun)),
                 fill=factor(scenario))) +
  xlab('Configuration') + ylab('Number of punishments') +
  ylim(0,100000) +
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
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_blank())
dev.off()


##
## Number of Mafiosi Imprisoned
##
data <- NULL
time <- seq(0,simLen-delta,delta)
p <- 1
for(i in content){
  dataM <- data.table(mafia[[i]])
  
  imp <- NULL
  for(t in time){
    imp <- rbind(imp, nrow(dataM[which(time == t & imprisoned == 'true'),]) / nReplicas)
  }
  
  data <- rbind(data, cbind(time, order[p], imp))
  p = p + 1
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
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.position = 'top',
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

png(filename=paste0(baseOutput,"/propPaidExt.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(paidExt))*100,
                 fill=factor(scenario))) +
  xlab('Configuration') + ylab('Proportion of paid pizzo') + ylim(0,100) +
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
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_blank())
dev.off()


##
## Proportion of Denounces
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
    
    nExtort <- nrow(subset(extortion[[i]], r == replica))
    
    nNPaid <- nrow(subset(extortion[[i]], (paid == "false" & r == replica)))
    
    propDenExt <- nDenExt / nExtort
    
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
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(propDen))*100,
                 fill=factor(scenario))) +
  xlab('Configuration') + ylab('Proportion of pizzo requests reported') + ylim(0,15) +
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
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_blank())
dev.off()


##
## Proportion of Punishment general
##
ppg <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in dirs){
  for(replica in replicas) {
    
    nExtortion <- nrow(subset(extortion[[i]], r == replica))
    
    nPun <- nrow(subset(extortion[[i]], (paid == "false" &
                                           mafiaPunished == "true" &
                                           r == replica)))
    
    ppg[replica+1,i] <-  nPun / nExtortion
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], ppg[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("scenario","propPun"))

png(filename=paste0(baseOutput,"/propPun.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(propPun))*100,
                 fill=factor(scenario))) +
  xlab('Configuration') + ylab('Proportion of punishments') + ylim(0,100) +
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
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_blank())
dev.off()


##
##  Proportion of Punishment per No Payment
##
pp <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in dirs){
  for(replica in replicas) {
    
    nNPaid <- nrow(subset(extortion[[i]], (paid == "false" &
                                             r == replica)))
    
    nPun <- nrow(subset(extortion[[i]], (paid == "false" &
                                           mafiaPunished == "true" &
                                           r == replica)))
    
    pp[replica+1,i] <-  nPun / nNPaid
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], pp[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("scenario","propPunNPay"))

png(filename=paste0(baseOutput,"/propPunNPay.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(propPunNPay))*100,
                 fill=factor(scenario))) +
  xlab('Configuration') + ylab('Proportion of punishments') + ylim(0,100) +
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
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_blank())
dev.off()


##
## Proportion of Investigation Leading to Imprisonment
##
pc <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in dirs){
  for(replica in replicas) {
    
    nExtort <- nrow(subset(extortion[[i]], r == replica))
    
    nInvCon <- nrow(subset(extortion[[i]], (paid == "false" &
                                              (investigatedExtortion == "true" |
                                                 investigatedPunishment == "true") &
                                              mafiosoConvicted == "true" &
                                              r == replica)))
    
    pc[replica+1,i] <-  nInvCon / nExtort
    
  }
}

data <- NULL
for(i in content){
  data <- rbind(data, cbind(order[i], pc[,i]))
}
data <- data.frame(data)
setnames(data,c("X1","X2"),c("scenario","propConv"))

png(filename=paste0(baseOutput,"/propImprisonment.png"), width=1024, height=768)
ggplot(data, aes(x=factor(scenario), y=as.numeric(as.character(propConv))*100,
                 fill=factor(scenario))) +
  xlab('Configuration') + ylab('Proportion of investigations\n leading to imprisonment') + ylim(0,5) +
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
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_blank())
dev.off()


##
## SALIENCE
##

##
## Pay Extortion
##
data <- NULL
time <- seq(0,simLen-delta,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  dataC <- data.table(consumer[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){  
    sal <- c(dataE[which(time == t),]$saliencePayExtortion)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  data <- rbind(data, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
data <- data.table(data)
setnames(data, c("time", "V2", "mV", "sV"),
         c("time", "treatment", "mV", "sV"))

png(filename=paste0(baseOutput,"/salPayExt.png"), width=1024, height=768)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(mV)),
                 group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,1) +
  xlab('Time units') + ylab('Norm salience of \n \'Pay pizzo\' ') +
  geom_line(size=1, colour="black") +
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.position = 'top',
        legend.text = element_text(colour="black", size=36, face="bold"),
        legend.title = element_text(colour="black", size=36, face="bold"))
dev.off()


##
## Not Pay Extortion
##
data <- NULL
time <- seq(0,simLen-delta,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  dataC <- data.table(consumer[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    sal <- c(dataE[which(time == t),]$salienceNotPayExtortion)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  data <- rbind(data, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
data <- data.table(data)
setnames(data, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

png(filename=paste0(baseOutput,"/salNotPayExt.png"), width=1024, height=768)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(mV)),
                 group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,1) +
  xlab('Time units') + ylab('Norm salience of \n \'Do not pay pizzo\' ') +
  geom_line(size=1, colour="black") +
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.position = 'top',
        legend.text = element_text(colour="black", size=36, face="bold"),
        legend.title = element_text(colour="black", size=36, face="bold"))
dev.off()


##
## Report Extortion
##
data <- NULL
time <- seq(0,simLen-delta,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  dataC <- data.table(consumer[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    sal <- c(dataE[which(time == t),]$salienceDenounce)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  data <- rbind(data, cbind(time, rep(order[p], length(mV)), mV, sV))
  p <- p + 1
}
data <- data.table(data)
setnames(data, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

png(filename=paste0(baseOutput,"/salRep.png"), width=1024, height=768)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(mV)),
                 group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,1) +
  xlab('Time units') + ylab('Norm salience of \n \'Report\' ') +
  geom_line(size=1, colour="black") +
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.position = 'top',
        legend.text = element_text(colour="black", size=36, face="bold"),
        legend.title = element_text(colour="black", size=36, face="bold"))
dev.off()


##
## Not Report Extortion
##
data <- NULL
time <- seq(0,simLen-delta,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  dataC <- data.table(consumer[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    sal <- c(dataE[which(time == t),]$salienceNotDenounce)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  data <- rbind(data, cbind(time, rep(order[p], length(mV)), mV, sV))
  p <- p + 1
}
data <- data.table(data)
setnames(data, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

png(filename=paste0(baseOutput,"/salNotRep.png"), width=1024, height=768)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(mV)),
                 group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,1) +
  xlab('Time Units') + ylab('Norm salience of \n \'Do not report\' ') +
  geom_line(size=1, colour="black") +
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.position = 'top',
        legend.text = element_text(colour="black", size=36, face="bold"),
        legend.title = element_text(colour="black", size=36, face="bold"))
dev.off()


##
## REPUTATION
##

##
## State Finder
##
data <- NULL
time <- seq(0,simLen-delta,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    sal <- c(dataE[which(time == t),]$reputationStateFinder)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  data <- rbind(data, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
data <- data.table(data)
setnames(data, c("time", "V2", "mV", "sV"),
         c("time", "treatment", "mV", "sV"))

png(filename=paste0(baseOutput,"/repStateFinder.png"), width=1024, height=768)
ggplot(data, aes(x=as.numeric(as.character(time)),
                       y=as.numeric(as.character(mV)),
                       group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,1) +
  xlab('Time units') + ylab('Reputation of the State as finder') +
  geom_line(size=1, colour="black") +
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.position = 'top',
        legend.text = element_text(colour="black", size=36, face="bold"),
        legend.title = element_text(colour="black", size=36, face="bold"))
dev.off()


##
## State Protector
##
data <- NULL
time <- seq(0,simLen-delta,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    sal <- c(dataE[which(time == t),]$reputationStateProtector)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  data <- rbind(data, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
data <- data.table(data)
setnames(data, c("time", "V2", "mV", "sV"),
         c("time", "treatment", "mV", "sV"))

png(filename=paste0(baseOutput,"/repStateProtector.png"), width=1024, height=768)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(mV)),
                 group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,1) +
  xlab('Time units') + ylab('Reputation of the State as protector') +
  geom_line(size=1, colour="black") +
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.position = 'top',
        legend.text = element_text(colour="black", size=36, face="bold"),
        legend.title = element_text(colour="black", size=36, face="bold"))
dev.off()


##
## Mafia Punisher
##
data <- NULL
time <- seq(0,simLen-delta,delta)
p <- 1
for(i in content){
  dataE <- data.table(entrepreneur[[i]])
  
  mV <- NULL
  sV <- NULL
  for(t in time){
    sal <- c(dataE[which(time == t),]$reputationMafiaPunisher)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  data <- rbind(data, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
data <- data.table(data)
setnames(data, c("time", "V2", "mV", "sV"),
         c("time", "treatment", "mV", "sV"))

png(filename=paste0(baseOutput,"/repMafiaPunisher.png"), width=1024, height=768)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(mV))*100,
                 group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,100) +
  xlab('Time units') + ylab('Reputation of the Mafia as punisher') +
  geom_line(size=1, colour="black") +
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank(),
        legend.position = 'top',
        legend.text = element_text(colour="black", size=36, face="bold"),
        legend.title = element_text(colour="black", size=36, face="bold"))
dev.off()


##
## PER CONFIGURATION GRAPHICS
##

for(i in dirs){
  
##
## SALIENCE HISTOGRAM
##

##
## Pay Pizzo
##
png(filename=paste0(base,dir[i],"/histSalPayExt.png"), width=1024, height=768)
ggplot(entrepreneur[[i]], aes(x=saliencePayExtortion)) +
  xlim(0,1) + ylim(0,100) +
  ylab('Number of Entrepreneurs') + xlab('Norm salience of \n \'Pay pizzo\' ') +
  geom_histogram(aes(y=..density..),
                 binwidth=.05, colour="black", fill="white", size=1.5) +
  geom_vline(aes(xintercept=mean(saliencePayExtortion, na.rm=T)),
             color="red", linetype="dashed", size=1.5) +
  geom_density(alpha=.05, fill="black", linetype="dotted", size=1.0) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank())
dev.off()


##
## Do Not Pay Pizzo
##
png(filename=paste0(base,dir[i],"/histSalNotPayExt.png"), width=1024, height=768)
ggplot(entrepreneur[[i]], aes(x=salienceNotPayExtortion)) +
  xlim(0,1) + ylim(0,100) +
  ylab('Number of Entrepreneurs') + xlab('Norm salience of \n \'Do not pay pizzo\' ') +
  geom_histogram(aes(y=..density..),
                 binwidth=.05, colour="black", fill="white", size=1.5) +
  geom_vline(aes(xintercept=mean(salienceNotPayExtortion, na.rm=T)),
             color="red", linetype="dashed", size=1.5) +
  geom_density(alpha=.05, fill="black", linetype="dotted", size=1.0) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank())
dev.off()


##
## Report
##
png(filename=paste0(base,dir[i],"/histSalRep.png"), width=1024, height=768)
ggplot(entrepreneur[[i]], aes(x=salienceDenounce)) +
  xlim(0,1) + ylim(0,100) +
  ylab('Number of Entrepreneurs') + xlab('Norm salience of \n \'Report\' ') +
  geom_histogram(aes(y=..density..),
                 binwidth=.05, colour="black", fill="white", size=1.5) +
  geom_vline(aes(xintercept=mean(salienceDenounce, na.rm=T)),
             color="red", linetype="dashed", size=1.5) +
  geom_density(alpha=.05, fill="black", linetype="dotted", size=1.0) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank())
dev.off()


##
## Do Not Report
##
png(filename=paste0(base,dir[i],"/histSalNotRep.png"), width=1024, height=768)
ggplot(entrepreneur[[i]], aes(x=salienceNotDenounce)) +
  xlim(0,1) + ylim(0,100) +
  ylab('Number of Entrepreneurs') + xlab('Norm salience of \n \'Do not report\' ') +
  geom_histogram(aes(y=..density..),
                 binwidth=.05, colour="black", fill="white", size=1.5) +
  geom_vline(aes(xintercept=mean(salienceNotDenounce, na.rm=T)),
             color="red", linetype="dashed", size=1.5) +
  geom_density(alpha=.05, fill="black", linetype="dotted", size=1.0) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_blank(),
        panel.grid.major = element_blank())
dev.off()

}

##
## DECISION
##

##
## Entreprenur Pay Decision
##
i <- 3
line <- sample(nrow(extortion[[i]]),1)
e <- extortion[[i]][line]$entrepreneurId
re <- extortion[[i]][line]$r

dataE <- data.table(extortion[[i]][which(entrepreneurId == e & r == re),])

atanPayIG <- (0.5 * atan2(0.01 * dataE$payIndividual, 1) / (pi / 2.0)) + 0.5
atanNPayIG <- (0.5 * atan2(0.01 * dataE$notPayIndividual, 1) / (pi / 2.0)) + 0.5

data <- NULL
data <- cbind(data, "salPay", dataE$time, dataE$paySalience)
data <- rbind(data, cbind("salNotPay", dataE$time, dataE$notPaySalience))
data <- rbind(data, cbind("id", dataE$time, (atanPayIG / (atanPayIG + atanNPayIG))))
data <- data.table(data)
setnames(data, c("V1", "V2", "V3"), c("var", "time", "val"))

ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(val)),
                 group=var, colour=as.factor(var))) +
  xlim(0,simLen) + ylim(0,1) +
  xlab('Time') + ylab('Value') +
  geom_line(size=2) +
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
## Entreprenur Denounce Decision
##
#i <- 1
#line <- sample(nrow(extortion[[i]]),1)
#e <- extortion[[i]][line]$entrepreneurId
#re <- extortion[[i]][line]$r

dataE <- data.table(extortion[[i]][which(entrepreneurId == e & r == re),])

data <- NULL
data <- cbind(data, "salDen", dataE$time, dataE$denounceSalience)
data <- rbind(data, cbind("salNotDen", dataE$time, dataE$notDenounceSalience))
data <- rbind(data, cbind("id", dataE$time, dataE$denounceIndividual))
data <- data.table(data)
setnames(data, c("V1", "V2", "V3"), c("var", "time", "val"))

ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(val)),
                 group=var, colour=as.factor(var))) +
  xlim(0,simLen) + ylim(0,1) +
  xlab('Time') + ylab('Value') +
  geom_line(size=2) +
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
## NORM SHIFT
##
n <- c(0,100,200,250,300,400)
for(i in dirs){
  aux <- data.table(entrepreneur[[i]])
  new <- nrow(aux[which((time == simLen) &
                          (saliencePayExtortion <= salienceNotPayExtortion) &
                          (salienceDenounce >= salienceNotDenounce)),])
  
  traditional <- nrow(aux[which((time == simLen) &
                                  (saliencePayExtortion > salienceNotPayExtortion) &
                                  (salienceDenounce < salienceNotDenounce)),])
  
  onlyNotPay <- nrow(aux[which((time == simLen) &
                                 (saliencePayExtortion <= salienceNotPayExtortion) &
                                 (salienceDenounce < salienceNotDenounce)),])
  
  onlyDenounce <- nrow(aux[which((time == simLen) &
                                   (saliencePayExtortion > salienceNotPayExtortion) &
                                   (salienceDenounce >= salienceNotDenounce)),])
  
  print(c(i, traditional, onlyNotPay, onlyDenounce, new, (new+traditional+onlyNotPay+onlyDenounce)))
  z <- i %% 6
  if(z == 0){
    z <- 6
  }
  print(c(i, (((ent-n[z])-traditional)/ent), onlyNotPay/ent, onlyDenounce/ent, ((new-n[z])/ent)))
}


##
## VALIDATION
##
city <- c("Agrigento",
          "Catania",
          "Messina",
          "Palermo",
          "Siracusa",
          "Trapani",
          "P1",
          "P2",
          "P3",
          "P4",
          "P5")

y <- c(0.630,0.845,0.759,0.669,0.833,0.333,as.array(propPaid))
x <- c(0.716,0.764,0.759,0.829,0.867,0.762,as.array(1 - propDen))

data <- data.table(cbind(city,x,y))

ggplot(data, aes(x=as.numeric(as.character(x))*100, y=as.numeric(as.character(y))*100)) +
  xlim(0,100) + ylim(0,100) +
  xlab('% Unreported Cases') + ylab('% Completed Extortions') +
  geom_point(aes(color=as.character(city)), size=6) +
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