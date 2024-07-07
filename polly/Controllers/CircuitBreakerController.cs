using System;
using System.Net.Http;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Polly;
using ResiliencePatterns.Polly.Models;

namespace ResiliencePatterns.Polly.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class CircuitBreakerController : Controller
    {
        private readonly BackendService _backendService;

        public CircuitBreakerController(BackendService backendService) 
        {
            _backendService = backendService;
        }

        [HttpPost]
        public async Task<ResilienceModuleMetrics> IndexAsync(Config config)
        {
            var cb = CreateCircuitBreaker();
            var metrics = await _backendService.MakeRequestAsync(cb, config);
            return metrics;
        }

        private AsyncPolicy CreateCircuitBreaker()
        {
            var exceptionsAllowedBeforeBreaking = Int32.Parse(Environment.GetEnvironmentVariable("EXCEPTIONS_ALLOWED_BEFORE_BREAKING"));
            var durationOfBreak = TimeSpan.FromMilliseconds(Int32.Parse(Environment.GetEnvironmentVariable("DURATION_OF_BREAK")));
            var failureThreshold = Int32.Parse(Environment.GetEnvironmentVariable("FAILURE_THRESHOLD"));
            var samplingDuration = TimeSpan.FromMilliseconds(Int32.Parse(Environment.GetEnvironmentVariable("SAMPLING_DURATION")));
            var minimumThroughput = Int32.Parse(Environment.GetEnvironmentVariable("MINIMUM_THROUGHPUT"));
            
            var policy = Policy.Handle<HttpRequestException>().Or<TaskCanceledException>();

            if (exceptionsAllowedBeforeBreaking > 0)
            {
                return policy.CircuitBreakerAsync(
                    exceptionsAllowedBeforeBreaking: exceptionsAllowedBeforeBreaking,
                    durationOfBreak: durationOfBreak
                );
            }
            return policy.AdvancedCircuitBreakerAsync(
                failureThreshold: failureThreshold,
                samplingDuration: samplingDuration,
                minimumThroughput: minimumThroughput,
                durationOfBreak: durationOfBreak
            );
        }
    }
}
