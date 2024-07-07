﻿namespace ResiliencePatterns.Polly.Models
{
    public class Config
    {
        public int MaxRequests { get; set; }
        public int SuccessfulRequests { get; set; }

        public string TargetUrl { get; set; }
    }
}
