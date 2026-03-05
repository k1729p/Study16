```mermaid
flowchart LR
 subgraph Docker Containers
  ELS[Elasticsearch]:::orangeBox
  KIB[Kibana]:::orangeBox
 end
 subgraph Clients
  WBR[Web\nBrowser]
  APL[Application]
 end

 WBR <--> KIB <--> ELS
 APL <--> ELS
 
 classDef orangeBox  fill:#ffa500,stroke:#000,stroke-width:3px
```