pe <- matrix(rep(0,max(dirs)*max(replicas+1)),nrow=max(replicas+1))
for(i in dirs){
  for(replica in replicas) {
  
    pe[replica+1,i] <- nrow(subset(extortion[[i]], (paid == "true" & r == replica))) /
      nrow(subset(extortion[[i]], r == replica))
    
  }
}

for(i in 1:5){
  for(j in 1:5){
    print(c(i,j,wilcox.test(pe[,i],pe[,j], paired=FALSE, alternative="two.sided")$p.value))
  }
}


wilcox.test(pe[,1],pe[,2], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(pe[,2],pe[,3], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(pe[,3],pe[,4], paired=FALSE, alternative="two.sided")$p.value
wilcox.test(pe[,4],pe[,5], paired=FALSE, alternative="two.sided")$p.value
