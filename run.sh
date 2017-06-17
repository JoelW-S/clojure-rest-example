helm install --name example --set persistence.enabled=false stable/mongodb
kubectl rollout status deployment example-mongodb
sleep 10
#db.user.ensureIndex({ email: 1}, { unique: true})
