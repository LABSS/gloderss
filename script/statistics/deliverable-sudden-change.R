##
## Libraries
##
library(ggplot2)
library(data.table)

##
## Hypothesis
##
#
# Nothing + Legal
#
baseOutput <- "/data/workspace/gloders/gloderss/output/figures/Resilience/H1-resilience"
content <- c(1,2)
order <- c('1.3','1.4')

#
# Nothing + Social
#
baseOutput <- "/data/workspace/gloders/gloderss/output/figures/Resilience/H2-resilience"
content <- c(1,3)
order <- c('2.5','2.7')

#
# Nothing + Combined
#
baseOutput <- "/data/workspace/gloders/gloderss/output/figures/Resilience/H3-resilience"
content <- c(1,4)
order <- c('2.5','3.8')

#
# Legal + Combined
#
baseOutput <- "/data/workspace/gloders/gloderss/output/figures/Resilience/H4-resilience"
content <- c(2,4)
order <- c('1.4','3.8')

#
# Legal + Combined -> Strong to Weak State
#
baseOutput <- "/data/workspace/gloders/gloderss/output/figures/Resilience/H5-resilience"
content <- c(2,4)
order <- c('Legal','Combined')

#
# Legal + Combined -> Violent to Hidden Mafia
#
baseOutput <- "/data/workspace/gloders/gloderss/output/figures/Resilience/H6-resilience"
content <- c(5,6)
order <- c('Legal','Combined')

wid <- 1536
hei <- 700

##
## UPLOAD
##
base <- "/data/workspace/gloders/gloderss/output/data/Resilience"

dir <- c("WeakState-NoNorm",
         "StrongState-to-WeakState",
         "SocialNorm-to-NoNorm",
         "Combined-to-WeakState-NoNorm",
         "StrongState-ViolentMafia-to-HiddenMafia",
         "Combined-ViolentMafia-to-HiddenMafia")

replicas <- 0:9

simLen <- 20000
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
## Number of Extortion
##
data <- NULL
p <- 1
for(i in content){
  for(r in seq(0,simLen-delta,delta)){
    min <- r
    max <- r + delta
    data <- rbind(data, c(order[p], r, nrow(subset(extortion[[i]],
                                                   (time >= min) & (time < max))) / nReplicas))
  }
  p = p + 1
}

data <- data.frame(data)
setnames(data,c('X1','X2','X3'),c('scenario','time','numExt'))

png(filename=paste0(baseOutput,"/numExt.png"), width=wid, height=hei)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(numExt)),
                 linetype=scenario)) +
  xlab('Time units') + ylab('Number of requests for pizzo made') + ylim(0,300) +
  geom_line(size=1, colour="black") +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        #panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        #legend.position = 'top',
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_text(colour="black", size=24, face="bold"),
        legend.key = element_rect(fill = "white"))
dev.off()


##
## Number of Punishment
##
data <- NULL
p <- 1
for(i in content){
  for(r in seq(0,simLen-delta,delta)){
    min <- r
    max <- r + delta
    data <- rbind(data, c(order[p], r, nrow(subset(extortion[[i]],
                                                   (paid == "false") &
                                                     (mafiaPunished == "true") &
                                                     (time >= min) &
                                                     (time < max))) / nReplicas))
  }
  p = p + 1
}

data <- data.frame(data)
setnames(data,c('X1','X2','X3'),c('scenario','time','numPun'))

png(filename=paste0(baseOutput,"/numPun.png"), width=wid, height=hei)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(numPun)),
                 linetype=scenario)) +
  xlab('Time units') + ylab('Number of punishments') +
  xlim(0,simLen) + ylim(0,100) +
  geom_line(size=1, colour="black") +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        #panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        #legend.position = 'top',
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_text(colour="black", size=24, face="bold"),
        legend.key = element_rect(fill = "white"))
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

png(filename=paste0(baseOutput,"/numImprisoned.png"), width=wid, height=hei)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(imp)),
                 group=treatment, linetype=as.factor(treatment))) +
  xlab('Time units') + ylab('Number of mafiosi imprisoned') +
  xlim(0,simLen) + ylim(0,20) +
  geom_line(size=1, colour="black") +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        #panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        #legend.position = 'top',
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_text(colour="black", size=24, face="bold"),
        legend.key = element_rect(fill = "white"))
dev.off()


##
## Proportion of Paid Extortion
##
data <- NULL
p <- 1
for(i in content){
  for(r in seq(0,simLen-delta,delta)){
    min <- r
    max <- r + delta
    data <- rbind(data, c(order[p], r, nrow(subset(extortion[[i]],
                                                   (paid == "true") &
                                                   (time >= min) & (time < max))) /
                            nrow(subset(extortion[[i]],
                                        (time >= min) & (time < max)))))
  }
  p = p + 1
}

data <- data.frame(data)
setnames(data,c('X1','X2','X3'),c('scenario','time','paidExt'))

png(filename=paste0(baseOutput,"/propPaidExt.png"), width=wid, height=hei)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(paidExt))*100,
                 linetype=scenario)) +
  xlab('Time units') + ylab('Proportion of paid pizzo') + ylim(0,100) +
  geom_line(size=1, colour="black") +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        #panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        #legend.position = 'top',
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_text(colour="black", size=24, face="bold"),
        legend.key = element_rect(fill = "white"))
dev.off()


##
## Proportion of Denunciation
##
data <- NULL
p <- 1
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
    
    data <- rbind(data, c(order[p], r, propDenExt + propDenPun))
  }
  p = p + 1
}

data <- data.frame(data)
setnames(data,c('X1','X2','X3'),c('scenario','time','propDen'))

png(filename=paste0(baseOutput,"/propDenExt.png"), width=wid, height=hei)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(propDen))*100,
                 linetype=scenario)) +
  xlab('Time units') + ylab('Proportion of pizzo requests reported') +
  ylim(0,100) +
  geom_line(size=1, colour="black") +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        #panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        #legend.position = 'top',
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_text(colour="black", size=24, face="bold"),
        legend.key = element_rect(fill = "white"))
dev.off()


##
## Proportion of Imprisonment
##
data <- NULL
p <- 1
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
    
    data <- rbind(data, c(order[p], r, nInvCon / nExtortion))
  }
  p = p + 1
}

data <- data.frame(data)
setnames(data,c('X1','X2','X3'),c('scenario','time','propConv'))

png(filename=paste0(baseOutput,"/propImprisonment.png"), width=wid, height=hei)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(propConv))*100,
                 linetype=scenario)) +
  xlab('Time units') + ylab('Proportion of investigations\n leading to imprisonment') +
  ylim(0,100) +
  geom_line(size=1, colour="black") +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        #panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        #legend.position = 'top',
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_text(colour="black", size=24, face="bold"),
        legend.key = element_rect(fill = "white"))
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
    #sal <- c(dataE[which(time == t),]$saliencePayExtortion,
    #         dataC[which(time == t),]$saliencePayExtortion)
    
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

png(filename=paste0(baseOutput,"/salPayExt.png"), width=wid, height=hei)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(mV)),
                 group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,1) +
  xlab('Time units') + ylab('Norm salience of \n \'Pay pizzo\' ') +
  geom_line(size=1, colour="black") +
  scale_linetype_discrete(name='Configuration') +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        #panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        #legend.position = 'top',
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_text(colour="black", size=24, face="bold"),
        legend.key = element_rect(fill = "white"))
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
    #sal <- c(dataE[which(time == t),]$salienceNotPayExtortion,
    #         dataC[which(time == t),]$salienceNotPayExtortion)
    
    sal <- c(dataE[which(time == t),]$salienceNotPayExtortion)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  data <- rbind(data, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
data <- data.table(data)
setnames(data, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

png(filename=paste0(baseOutput,"/salNotPayExt.png"), width=wid, height=hei)
ggplot(data, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,1) +
  xlab('Time units') + ylab('Norm salience of \n \'Do not pay pizzo\' ') +
  geom_line(size=1) +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        #panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        #legend.position = 'top',
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_text(colour="black", size=24, face="bold"),
        legend.key = element_rect(fill = "white"))
dev.off()


##
## Denounce Extortion
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
    #sal <- c(dataE[which(time == t),]$salienceDenounce,
    #         dataC[which(time == t),]$salienceDenounce)
    
    sal <- c(dataE[which(time == t),]$salienceDenounce)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  data <- rbind(data, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
data <- data.table(data)
setnames(data, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

png(filename=paste0(baseOutput,"/salRep.png"), width=wid, height=hei)
ggplot(data, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,1) +
  xlab('Time Units') + ylab('Norm salience of \n \'Report\' ') +
  geom_line(size=1, colour="black") +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        #panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        #legend.position = 'top',
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_text(colour="black", size=24, face="bold"),
        legend.key = element_rect(fill = "white"))
dev.off()


##
## Not Denounce Extortion
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
    #sal <- c(dataE[which(time == t),]$salienceNotDenounce,
    #        dataC[which(time == t),]$salienceNotDenounce)
    
    sal <- c(dataE[which(time == t),]$salienceNotDenounce)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  data <- rbind(data, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
data <- data.table(data)
setnames(data, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

png(filename=paste0(baseOutput,"/salNotRep.png"), width=wid, height=hei)
ggplot(data, aes(x=as.numeric(as.character(time)),
                 y=as.numeric(as.character(mV)),
                 group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0,1) +
  xlab('Time units') + ylab('Norm salience of \n \'Do not report\' ') +
  geom_line(size=1, colour="black") +
  geom_vline(aes(xintercept=simLen/2),
             color="black", linetype="dashed", size=1.5) +
  scale_linetype_discrete(name='Configuration') +
  theme(axis.title.x = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        #panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        #panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        #legend.position = 'top',
        legend.text = element_text(colour="black", size=24, face="bold"),
        legend.title = element_text(colour="black", size=24, face="bold"),
        legend.key = element_rect(fill = "white"))
dev.off()


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