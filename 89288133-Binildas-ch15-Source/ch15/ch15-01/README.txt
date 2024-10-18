Sample using Docker Repository
Browser => ProductWeb μS ==> In-Memory-Data

AWS EKS Console:
https://ap-southeast-1.console.aws.amazon.com/eks/home?region=ap-southeast-1#/clusters

===============================================================
Build Product Web
-----------------
Assuming ch01/ch01-01 (or ch07/ch07-03) sample has been built and image uploaded to docker hub

SET UP EKS

(base) binildass-MBP:ch15-01 binil$ terraform init
(base) binildass-MBP:ch15-01 binil$ terraform validate
Success! The configuration is valid.

(base) binildass-MBP:ch15-01 binil$ terraform apply
aws_eks_node_group.demo: Creation complete after 2m12s [id=bdca-tf-eks-01:demo]

Apply complete! Resources: 18 added, 0 changed, 0 destroyed.

Outputs:

config_map_aws_auth = <<EOT
apiVersion: v1
kind: ConfigMap
metadata:
  name: aws-auth
  namespace: kube-system
data:
  mapRoles: |
    - rolearn: arn:aws:iam::023577096755:role/terraform-eks-demo-node
      username: system:node:{{EC2PrivateDNSName}}
      groups:
        - system:bootstrappers
        - system:nodes

EOT
kubeconfig = <<EOT
apiVersion: v1
clusters:
- cluster:
    server: https://0F081B762C5C9EAF004A25B54FFF8841.gr7.ap-southeast-1.eks.amazonaws.com
    certificate-authority-data: LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUM1ekNDQWMrZ0F3SUJBZ0lCQURBTkJna3Foa2lHOXcwQkFRc0ZBREFWTVJNd0VRWURWUVFERXdwcmRXSmwKY201bGRHVnpNQjRYRFRJeE1EVXlPVEExTXpJMU9Gb1hEVE14TURVeU56QTFNekkxT0Zvd0ZURVRNQkVHQTFVRQpBeE1LYTNWaVpYSnVaWFJsY3pDQ0FTSXdEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBRENDQVFvQ2dnRUJBT25BCkV5ZTJWMlE3dlhZVDdxd3E1aEhVUkVxMUdTVTNFR1ZFOGdFV1ZhQ1N4YzhSUWtHalcrUU5aZnFWU0ZwRDRXemIKLzZNeEI5MWJjeThCYzVpOFc3bUVXWGltUXlLaTI1T05UcEVjVDB0UXNMc0xaUUdxMzEwWTFRU2VES1U0bXlRagpJRHh0RktzYnBJOVp4ZEZuSEhJN0dZd0Z5a09hQTl4OFQ4QUxLQnBGbW4wR2RJejdHRzU1cXJnMTdwVUFEYk91ClpWNzNHZHlZaFFwZ3lNa1RKcnVVcG1CVEtEVGJGbVhkNjQvdk9uaDJCRWY3c2R3bUJFQzE3ZWpmN0o3dTdaOGQKbDk1NGExNmJ1YXh6TkNjZEErVWlVWTMwM0Z6em1uZG1kUjU5dHVieUd1cWFJVllSd0N1cEZIdGNSMVQ3dzV4WQpkUDZ2MURFdG05YjZmSktYWEwwQ0F3RUFBYU5DTUVBd0RnWURWUjBQQVFIL0JBUURBZ0trTUE4R0ExVWRFd0VCCi93UUZNQU1CQWY4d0hRWURWUjBPQkJZRUZQcm0wSjEyQm12Tk14R2ZpcHlrVFZzUE9uK2hNQTBHQ1NxR1NJYjMKRFFFQkN3VUFBNElCQVFBcnhtQXhzZ0FTeW1TU3ZOQVpiRExsbFo0WHlwZEVYOWtONk91UzgwZGxHUElQWWV6TgozS0U1bi92d3ZkRDkrdVBoOFFKcE5GdTRhVU9OYkNOSTFNNHp2UllNbm5uK0JuSFRCT3Vxbk83Y1RPdnY3VDZPCm9ldDdxK1poUzVjRVhYaGhkTHAwLzlQa08vVlhkRzdaekNWUVhENStCaXFaY1gzMXpydFhtNHRiZkN3K3Q0UGsKOTdhUTVCeGxIRnZSdURLNlBaQlpBdDJPWjFqdy8vK3NaY2kra0x5cnBMdWs1M2JjMTZFc3hvWTE2T1hUdm95UApnQllIcnNiLzcxdGt0a1l3RjJDY29jOXB3SVpUUVJ2b1VJVkxZT1V4ZjQ3d1Y5K0xBa3dGUWxXTU1QeXZ3UXdPCklqSUlBcHVGdGozWENxU2VXR3loZHZHYU95TW4xR0pwc0QyNwotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0tCg==
  name: kubernetes
contexts:
- context:
    cluster: kubernetes
    user: aws
  name: aws
current-context: aws
kind: Config
preferences: {}
users:
- name: aws
  user:
    exec:
      apiVersion: client.authentication.k8s.io/v1alpha1
      command: aws-iam-authenticator
      args:
        - "token"
        - "-i"
        - "bdca-tf-eks-01"

(base) binildass-MBP:ch15-01 binil$

===============================================================

CONFIGURE KUBECTL FOR AWS EKS
DEPLOY FROM LOCAL TO AWS EKS AND EXECUTE CLIENT

Last login: Sat Jul  3 10:01:11 on ttys000
(base) binildass-MBP:ch15-01 binil$ pwd
/Users/binil/binil/code/mac/mybooks/docker-04/ch15/ch15-01
(base) binildass-MBP:ch15-01 binil$ aws eks --region ap-southeast-1 update-kubeconfig --name bdca-tf-eks-01
Updated context arn:aws:eks:ap-southeast-1:023577096755:cluster/bdca-tf-eks-01 in /Users/binil/.kube/config
(base) binildass-MBP:ch15-01 binil$ kubectl get pods -l 'app=nginx' -o wide | awk {'print $1" " $3 " " $6'} | column -t
No resources found in default namespace.
(base) binildass-MBP:ch15-01 binil$ kubectl get pods -l 'app=product' -o wide | awk {'print $1" " $3 " " $6'} | column -t
No resources found in default namespace.
(base) binildass-MBP:ch15-01 binil$ kubectl apply -f product-deployment.yaml
deployment.apps/product-deployment created
(base) binildass-MBP:ch15-01 binil$ kubectl get pods -l 'app=product' -o wide | awk {'print $1" " $3 " " $6'} | column -t
NAME                                STATUS             IP
product-deployment-84b9777c5-9kpz8  ContainerCreating  <none>
(base) binildass-MBP:ch15-01 binil$ kubectl get pods -l 'app=product' -o wide | awk {'print $1" " $3 " " $6'} | column -t
NAME                                STATUS   IP
product-deployment-84b9777c5-9kpz8  Running  10.0.0.180
(base) binildass-MBP:ch15-01 binil$ kubectl get svc
NAME         TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
kubernetes   ClusterIP   172.20.0.1   <none>        443/TCP   11m
(base) binildass-MBP:ch15-01 binil$ kubectl create -f product-service.yaml
service/product-service-loadbalancer created
(base) binildass-MBP:ch15-01 binil$ kubectl get svc
NAME                           TYPE           CLUSTER-IP      EXTERNAL-IP                                                                    PORT(S)          AGE
kubernetes                     ClusterIP      172.20.0.1      <none>                                                                         443/TCP          12m
product-service-loadbalancer   LoadBalancer   172.20.15.199   a2473f05176a6444bb676cf3927fd363-1434093128.ap-southeast-1.elb.amazonaws.com   8080:32087/TCP   49s
(base) binildass-MBP:ch15-01 binil$ kubectl get service/product-service-loadbalancer |  awk {'print $1" " $2 " " $4 " " $5'} | column -t
NAME                          TYPE          EXTERNAL-IP                                                                   PORT(S)
product-service-loadbalancer  LoadBalancer  a2473f05176a6444bb676cf3927fd363-1434093128.ap-southeast-1.elb.amazonaws.com  8080:32087/TCP
(base) binildass-MBP:ch15-01 binil$ 

Test the Client
---------------
Open below link in Chrome

http://a2473f05176a6444bb676cf3927fd363-1434093128.ap-southeast-1.elb.amazonaws.com:8080/product.html

(base) binildass-MBP:ch15-01 binil$ curl -silent http://a2473f05176a6444bb676cf3927fd363-1434093128.ap-southeast-1.elb.amazonaws.com:8080/product.html | grep title
<title>Bootstrap CRUD Data Table for Database with Modal Form</title>
	.table-title {        
    .table-title h2 {
	.table-title .btn-group {
	.table-title .btn {
	.table-title .btn i {
	.table-title .btn span {
    .modal .modal-title {
            <div class="table-title">
							<td><span ng-bind="p.title"></span></td>
									data-toggle="tooltip" title="Edit">&#xE254;</i></a> <a
									class="material-icons" data-toggle="tooltip" title="Delete" ng-click="openDeleteForm(p)">&#xE872;</i></a>
						<h4 class="modal-title">Product Details</h4>
							<textarea class="form-control" ng-model="product.title" required></textarea>
						<h4 class="modal-title">Delete Product</h4>
(base) binildass-MBP:ch15-01 binil$ 

===============================================================

(base) binildass-MBP:ch15-01 binil$ terraform destroy

===============================================================


