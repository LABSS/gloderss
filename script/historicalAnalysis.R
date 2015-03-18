content <- c(1,49,61,67,68,69,70,71,72,43,44,45,46,47,48)
order <- c('S1/0','S2/0','S3/0','S4/0','S4/20','S4/40','S4/50','S4/60','S4/80',
           'S5/0','S5/20','S5/40','S5/50','S5/60','S5/80')
shapes <- c(15,17,3,18,18,18,18,18,18,20,20,20,20,20,20)
sizes <- c(10,10,10,10,12,14,16,18,20,10,12,14,16,18,20)

# 0 innovators
content <- c(1,49,61,67,43)
order <- c('S1','S2','S3','S4','S5')
shapes <- c(15,16,17,18,19)
sizes <- c(10,10,10,10,10)
fills <- c("black","black","black","black","black")
lines <- c(1,2,3,4,6)

# 20% 50% and 80% of innovators
content <- c(67,68,70,43,44,46)
order <- c('S4','S4/20','S4/50','S5','S5/20','S5/50')
shapes <- c(17,17,17,18,18,18)
sizes <- c(10,14,18,10,14,18)
fills <- c("black","black","black","black","black","black")
lines <- c(1,2,3,4,6)

##
## Scatterplot of Extortions
##
data <- data.table(cbind(order,dir[content],nExtortion[content]))
setnames(data,c("V2","V3"),c("dir","nExtortion"))
png(filename=paste0(base,"/numExtortionH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(nExtortion)))) +
  xlab('') + ylab('Number of Extortions') + ylim(0,10000) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  # geom_point(shape=shapes, fill=fills, size=sizes) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent", colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
#legend.position = 'none')
dev.off()

#legend.title = element_blank(),
#legend.text = element_text(colour="black", size=24, face="bold"))


##
## Scatterplot of Paid of Extortion
##
data <- data.table(cbind(order,dir[content],nPaid[content]))
setnames(data,c("V2","V3"),c("dir","nPaid"))
png(filename=paste0(base,"/numPaidExtH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(nPaid)))) +
  xlab('') + ylab('Number of Paid Extortions') + ylim(0,27000) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  # geom_point(shape=shapes, fill=fills, size=sizes) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
#legend.position = 'none')
dev.off()

##
## Scatterplot of Proportion of Paid of Extortions
##
data <- data.table(cbind(order,dir[content],propPaid[content]))
setnames(data,c("V2","V3"),c("dir","propPaid"))
png(filename=paste0(base,"/propPaidExtH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(propPaid)))) +
  xlab('') + ylab('% Paid Extortions') + ylim(0,1) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  # geom_point(shape=shapes, fill=fills, size=sizes) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
# legend.position = 'none')
dev.off()

##
## Scatterplot of Proportion of Punishment general
##
data <- data.table(cbind(order,dir[content],propPun[content]))
setnames(data,c("V2","V3"),c("dir","propPun"))
png(filename=paste0(base,"/propPunH.png"), width=1024, height=768)
ggplot(data, aes(x=order,y=as.numeric(as.character(propPun)))) +
  xlab('') + ylab('% Punishments') + ylim(0,1) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  # geom_point(aes(colour=as.character(order)), size=10) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
# legend.position = 'none')
dev.off()

##
## Scatterplot of Proportion of Punishment per No Payment
##
data <- data.table(cbind(order,dir[content],propPunNPay[content]))
setnames(data,c("V2","V3"),c("dir","propPunNPay"))
png(filename=paste0(base,"/propPunNPayH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(propPunNPay)))) +
  xlab('') + ylab('% Punishments') + ylim(0,1) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  # geom_point(shape=shapes, fill=fills, size=sizes) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
# legend.position = 'none')
dev.off()

##
## Scatterplot of Denounce
##
data <- data.table(cbind(order,dir[content],nDen[content]))
setnames(data,c("V2","V3"),c("dir","nDen"))
png(filename=paste0(base,"/numDenounceH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(nDen)))) +
  xlab('') + ylab('Number of Denounces') + ylim(0,500) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  # geom_point(shape=shapes, fill=fills, size=sizes) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
# legend.position = 'none')
dev.off()

##
## Scatterplot of Proportion of Denounces of Extortion
##
data <- data.table(cbind(order,dir[content],propDenExt[content]))
setnames(data,c("V2","V3"),c("dir","propDenExt"))
png(filename=paste0(base,"/propDenExtH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(propDenExt)))) +
  xlab('') + ylab('% Denounced Extortions') + ylim(0,1) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  # geom_point(shape=shapes, fill=fills, size=sizes) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
# legend.position = 'none')
dev.off()

##
## Scatterplot of Proportion of Denounces of Punishments
##
data <- data.table(cbind(order,dir[content],propDenPun[content]))
setnames(data,c("V2","V3"),c("dir","propDenPun"))
png(filename=paste0(base,"/propDenPunH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(propDenPun)))) +
  xlab('') + ylab('% Denounces Punishments') + ylim(0,1) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  # geom_point(shape=shapes, fill=fills, size=sizes) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 0.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=0.5, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=0.5, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
# legend.position = 'none')
dev.off()

##
## Scatterplot of Proportion of Denounces
##
propDen <- propDenExt + propDenPun
data <- data.table(cbind(order,dir[content],propDen[content]))
setnames(data,c("V2","V3"),c("dir","propDen"))
png(filename=paste0(base,"/propDenH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(propDen)))) +
  xlab('') + ylab('% Denounces') + ylim(0,1) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  #geom_point(aes(shape=as.character(order)), size=10) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
# legend.position = 'none')
dev.off()

##
## Scatterplot of Proportion of Imprisonment
##
data <- data.table(cbind(order,dir[content],nInvCon[content]/nExtortion[content]))
setnames(data,c("V2","V3"),c("dir","nInvCon"))
png(filename=paste0(base,"/propImprisonmentH.png"), width=1024, height=768)
ggplot(data, aes(x=order, y=as.numeric(as.character(nInvCon)))) +
  xlab('') + ylab('Number of Imprisonment') + ylim(0,1) +
  geom_point(aes(shape=as.character(order)), fill=fills, size=sizes) +
  #geom_point(aes(shape=as.character(order)), size=10) +
  theme(axis.title.x = element_blank(),
        axis.title.y = element_text(colour = 'black', size = 36, face = 'bold'),
        axis.text.x = element_blank(),
        axis.text.y = element_text(colour = 'black', size = 24, face = 'bold'),
        axis.line = element_line(colour = 'black', size = 1.5, linetype = 'solid'),
        panel.background = element_rect(fill = "transparent",colour = NA),
        panel.grid.minor = element_line(color='gray', size=1.0, linetype='dotted'),
        panel.grid.major = element_line(color='gray', size=1.0, linetype='dotted'),
        legend.title = element_blank(),
        legend.text = element_text(colour="black", size=24, face="bold"))
# legend.position = 'none')
dev.off()

##
## Norms' set shift
##
n <- c(0,60,120,150,180,240)
nShift <- NULL
for(i in content){
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

  aux <- data.table(consumer[[i]])
  new <- new + nrow(aux[which((time == simLen) &
                          (saliencePayExtortion <= salienceNotPayExtortion) &
                          (salienceDenounce >= salienceNotDenounce)),])
  
  traditional <- traditional + nrow(aux[which((time == simLen) &
                                  (saliencePayExtortion > salienceNotPayExtortion) &
                                  (salienceDenounce < salienceNotDenounce)),])
  
  onlyNotPay <- onlyNotPay + nrow(aux[which((time == simLen) &
                                 (saliencePayExtortion <= salienceNotPayExtortion) &
                                 (salienceDenounce < salienceNotDenounce)),])
  
  onlyDenounce <- onlyDenounce + nrow(aux[which((time == simLen) &
                                   (saliencePayExtortion > salienceNotPayExtortion) &
                                   (salienceDenounce >= salienceNotDenounce)),])
  
  z <- i %% 6
  if(z == 0){
    z <- 6
  }
  iTrad <- ((ent+con)-n[z])
  nShift <- rbind(nShift, c(i, ((iTrad-traditional)/iTrad), onlyNotPay/iTrad,
                            onlyDenounce/iTrad, ((new-n[z])/iTrad)))
}

write.table(nShift, file=paste0(base,"/nShift.csv"),
            quote=FALSE, append=FALSE, sep=";", col.names=FALSE, row.names=FALSE)

##
## Validation Information
##
city <- c("Agrigento",
          "Catania",
          "Messina",
          "Siracusa",
          "Snpun-nnorm-Mhard-IOinactive/0",
          "Snpun-nnorm-Mhard-IOinactive/20",
          "Snpun-nnorm-Mhard-IOinactive/40",
          "Snpun-nnorm-Mhard-IOinactive/50",
          "Snpun-nnorm-Mhard-IOinactive/60",
          "Snpun-nnorm-Mhard-IOinactive/80",
          "Snpun-nnorm-Mhard-IOactive/0",
          "Snpun-nnorm-Mhard-IOactive/20",
          "Snpun-nnorm-Mhard-IOactive/40",
          "Snpun-nnorm-Mhard-IOactive/50",
          "Snpun-nnorm-Mhard-IOactive/60",
          "Snpun-nnorm-Mhard-IOactive/80",
          "Snpun-nnorm-Msoft-IOinactive/0",
          "Snpun-nnorm-Msoft-IOinactive/20",
          "Snpun-nnorm-Msoft-IOinactive/40",
          "Snpun-nnorm-Msoft-IOinactive/50",
          "Snpun-nnorm-Msoft-IOinactive/60",
          "Snpun-nnorm-Msoft-IOinactive/80",
          "Snpun-nnorm-Msoft-IOactive/0",
          "Snpun-nnorm-Msoft-IOactive/20",
          "Snpun-nnorm-Msoft-IOactive/40",
          "Snpun-nnorm-Msoft-IOactive/50",
          "Snpun-nnorm-Msoft-IOactive/60",
          "Snpun-nnorm-Msoft-IOactive/80",
          "Spun-norm-Mhard-IOinactive/0",
          "Spun-norm-Mhard-IOinactive/20",
          "Spun-norm-Mhard-IOinactive/40",
          "Spun-norm-Mhard-IOinactive/50",
          "Spun-norm-Mhard-IOinactive/60",
          "Spun-norm-Mhard-IOinactive/80",
          "Spun-norm-Mhard-IOactive/0",
          "Spun-norm-Mhard-IOactive/20",
          "Spun-norm-Mhard-IOactive/40",
          "Spun-norm-Mhard-IOactive/50",
          "Spun-norm-Mhard-IOactive/60",
          "Spun-norm-Mhard-IOactive/80",
          "Spun-norm-Msoft-IOinactive/0",
          "Spun-norm-Msoft-IOinactive/20",
          "Spun-norm-Msoft-IOinactive/40",
          "Spun-norm-Msoft-IOinactive/50",
          "Spun-norm-Msoft-IOinactive/60",
          "Spun-norm-Msoft-IOinactive/80",
          "Spun-norm-Msoft-IOactive/0",
          "Spun-norm-Msoft-IOactive/20",
          "Spun-norm-Msoft-IOactive/40",
          "Spun-norm-Msoft-IOactive/50",
          "Spun-norm-Msoft-IOactive/60",
          "Spun-norm-Msoft-IOactive/80",
          "Spun-nnorm-Mhard-IOinactive/0",
          "Spun-nnorm-Mhard-IOinactive/20",
          "Spun-nnorm-Mhard-IOinactive/40",
          "Spun-nnorm-Mhard-IOinactive/50",
          "Spun-nnorm-Mhard-IOinactive/60",
          "Spun-nnorm-Mhard-IOinactive/80",
          "Spun-nnorm-Mhard-IOactive/0",
          "Spun-nnorm-Mhard-IOactive/20",
          "Spun-nnorm-Mhard-IOactive/40",
          "Spun-nnorm-Mhard-IOactive/50",
          "Spun-nnorm-Mhard-IOactive/60",
          "Spun-nnorm-Mhard-IOactive/80",
          "Spun-nnorm-Msoft-IOinactive/0",
          "Spun-nnorm-Msoft-IOinactive/20",
          "Spun-nnorm-Msoft-IOinactive/40",
          "Spun-nnorm-Msoft-IOinactive/50",
          "Spun-nnorm-Msoft-IOinactive/60",
          "Spun-nnorm-Msoft-IOinactive/80",
          "Spun-nnorm-Msoft-IOactive/0",
          "Spun-nnorm-Msoft-IOactive/20",
          "Spun-nnorm-Msoft-IOactive/40",
          "Spun-nnorm-Msoft-IOactive/50",
          "Spun-nnorm-Msoft-IOactive/60",
          "Spun-nnorm-Msoft-IOactive/80")

y <- c(0.65,0.85,0.78,0.82,as.array(propPaid[content]))
x <- c(0.70,0.75,0.74,0.85,as.array(1 - (propDenExt[content] + propDenPun[content])))
c <- city[c(1,2,3,4,(content+4))]

data <- data.table(cbind(c("Agrigento","Catania","Messina","Siracusa",order),c,x,y))

png(filename=paste0(base,"/validationI.png"), width=1024, height=768)
ggplot(data, aes(x=as.numeric(as.character(x))*100, y=as.numeric(as.character(y))*100)) +
  xlim(0,100) + ylim(0,100) +
  xlab('% Unreported Cases') + ylab('% Completed Extortions') +
  geom_point(aes(colour=as.character(c("Agrigento","Catania","Messina","Siracusa",order))), size=6) +
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
## Graphics Salience
##

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
    sal <- c(dataE[which(time == t),]$saliencePayExtortion,
             dataC[which(time == t),]$saliencePayExtortion)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  salience <- rbind(salience, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
salience <- data.table(salience)
setnames(salience, c("time", "V2", "mV", "sV"),
         c("time", "treatment", "mV", "sV"))

png(filename=paste0(base,"/salPayExtH.png"), width=1024, height=768)
ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0.25,0.75) +
  xlab('Time Units') + ylab('\'Pay Extortion\' Norm Salience') +
  geom_line(size=2) +
  #coord_cartesian(ylim=c(0.25, 0.75)) + 
  #scale_y_continuous(breaks=seq(0.25, 0.75, 0.10)) +
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
dev.off()


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
    sal <- c(dataE[which(time == t),]$salienceNotPayExtortion,
             dataC[which(time == t),]$salienceNotPayExtortion)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  salience <- rbind(salience, cbind(time, rep(order[p], length(mV)), mV, sV))
  p = p + 1
}
salience <- data.table(salience)
setnames(salience, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

png(filename=paste0(base,"/salNotPayExtH.png"), width=1024, height=768)
ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0.25,0.75) +
  xlab('Time Units') + ylab('\'Do Not Pay Extortion\' Norm Salience') +
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
dev.off()

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
    sal <- c(dataE[which(time == t),]$salienceDenounce,
             dataC[which(time == t),]$salienceDenounce)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  salience <- rbind(salience, cbind(time, rep(order[p], length(mV)), mV, sV))
  p <- p + 1
}
salience <- data.table(salience)
setnames(salience, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

png(filename=paste0(base,"/salDenExtH.png"), width=1024, height=768)
ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0.25,0.75) +
  xlab('Time Units') + ylab('\'Denounce Extortion\' Norm Salience') +
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
dev.off()


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
    sal <- c(dataE[which(time == t),]$salienceNotDenounce,
             dataC[which(time == t),]$salienceNotDenounce)
    mV <- c(mV, mean(sal))
    sV <- c(sV, sd(sal))
  }
  
  salience <- rbind(salience, cbind(time, rep(order[p], length(mV)), mV, sV))
  p <- p + 1
}
salience <- data.table(salience)
setnames(salience, c("time", "V2","mV","sV"), c("time", "treatment","mV","sV"))

png(filename=paste0(base,"/salNotDenExtH.png"), width=1024, height=768)
ggplot(salience, aes(x=as.numeric(as.character(time)),
                     y=as.numeric(as.character(mV)),
                     group=treatment, linetype=as.factor(treatment))) +
  xlim(0,simLen) + ylim(0.25,0.75) +
  xlab('Time Units') + ylab('\'Do Not Denounce Extortion\' Norm Salience') +
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
dev.off()