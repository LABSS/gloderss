library(ggplot2)
library(data.table)

slopes <- c(0.01, 0.1, 1)
x <- seq(0,100,0.01)

aux <- NULL
for(slope in slopes) {
  t <- (exp(slope*x) - 1) / (exp(slope*x) + 1)
  
  aux <- rbind(aux, cbind(x,slope,t)) 
}

data <- NULL
data <- data.table(aux)
  
ggplot(data, aes(x=as.numeric(as.character(x)),
                 y=as.numeric(as.character(t)),
                 group=slope,
                 linetype=as.factor(slope),
                 colour=as.factor(slope))) +
  xlab('Evidence') + ylab('Probability of spreading social norms') +
  xlim(0,100) + ylim(0,1) +
  geom_line(size=1) +
  scale_linetype_discrete(name='Slope') +
  scale_colour_discrete(name='Slope') +
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