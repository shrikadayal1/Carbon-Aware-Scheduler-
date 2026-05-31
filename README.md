# Carbon-Aware Scheduler

A Java-based Carbon-Aware Task Scheduling System that optimizes workload execution by considering carbon intensity and energy consumption metrics. The project compares traditional scheduling approaches with carbon-aware scheduling strategies to reduce environmental impact while maintaining system performance.

## Overview

Data centers and cloud systems consume significant amounts of energy, contributing to carbon emissions. This project simulates workload scheduling strategies and evaluates how carbon-aware scheduling can reduce emissions compared to conventional scheduling methods.

The system analyzes task execution patterns, carbon intensity data, and scheduling decisions to identify environmentally sustainable execution plans.

---

## Features

- Carbon-Aware Scheduling
- Workload Simulation
- Carbon Emission Analysis
- Baseline vs Carbon-Aware Comparison
- Performance Visualization
- Automated Simulation Reports
- CSV-Based Result Export

---

## Project Architecture

```text
Task Input
     │
     ▼
Carbon Intensity Data
     │
     ▼
Scheduling Engine
     │
 ┌───┴─────────────┐
 │                 │
 ▼                 ▼
Baseline      Carbon-Aware
Scheduler      Scheduler
 │                 │
 └──────┬──────────┘
        ▼
 Performance & Carbon Analysis
        ▼
 Graphs and Reports
```

---

## Tech Stack

- Java
- Maven
- CSV Processing
- Scheduling Algorithms
- Data Analysis
- Simulation Framework

---

## Project Structure

```text
CarbonAwareScheduler/
│
├── src/
├── resources/
├── docs/
├── simulation_output/
│
├── baseline_simulation.csv
├── carbon_aware_simulation.csv
├── carbon_simulation_log.csv
│
├── Carbon_vs_Time.png
├── Carbon_vs_Time_Auto.png
├── CES_Comparison.png
├── Action_Stacked_Bar.png
│
└── pom.xml
```

---

## Key Components

### Baseline Scheduler
Executes tasks using conventional scheduling methods without considering carbon emissions.

### Carbon-Aware Scheduler
Schedules tasks by incorporating carbon intensity and sustainability metrics to minimize environmental impact.

### Simulation Engine
Runs workload simulations and records execution metrics.

### Analytics Module
Generates comparative graphs and reports for evaluation.

---

## Results

The project evaluates:

- Carbon Emissions Reduction
- Scheduling Efficiency
- Task Completion Performance
- Energy Consumption Trends
- Environmental Sustainability Impact

Generated outputs include:

- Carbon vs Time Analysis
- Carbon Emission Comparison
- Scheduling Action Distribution
- Simulation Logs

---

## How to Run

### Clone Repository

```bash
git clone https://github.com/your-username/Carbon-Aware-Scheduler.git
```

### Build Project

```bash
mvn clean install
```

### Run Simulation

```bash
mvn exec:java
```

### View Results

Generated outputs can be found in:

```text
simulation_output/
```

---

## Applications

- Green Cloud Computing
- Sustainable Data Centers
- Energy-Efficient Scheduling
- Carbon Footprint Optimization
- Environmental Computing Research

---

## Future Enhancements

- Real-Time Carbon Intensity APIs
- Cloud Deployment Integration
- Machine Learning-Based Scheduling
- Dynamic Resource Allocation
- Multi-Region Carbon Optimization

---

## Author

Shrika Dayal

B.Tech CSE & Business Systems, VIT Vellore

AI/ML Enthusiast | Software Developer | Finance & Technology Enthusiast
