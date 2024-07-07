using System;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Polly;
using ResiliencePatterns.Polly.Models;

namespace ResiliencePatterns.Polly.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class RetryController : Controller
    {
        private readonly BackendService _backendService;

        public RetryController(BackendService backendService)
        {
            _backendService = backendService;
        }

        [HttpPost]
        public async Task<ResilienceModuleMetrics> IndexAsync(Config config)
        {
            var retry = CreateRetryExponencialBackoff();
            var metrics = await _backendService.MakeRequestAsync(retry, config);
            return metrics;
        }

        private static AsyncPolicy CreateRetryExponencialBackoff()
        {
            var retryCount = Int32.Parse(Environment.GetEnvironmentVariable("COUNT") ?? "3");
            var exponentialBackoffPow = Int32.Parse(Environment.GetEnvironmentVariable("EXPONENTIAL_BACKOFF_POW") ?? "2");
            var sleepDuration = TimeSpan.FromMilliseconds(Int32.Parse(Environment.GetEnvironmentVariable("SLEEP_DURATION") ?? "1000")).TotalSeconds;
            
            return Policy
                .Handle<Exception>()
                .WaitAndRetryAsync(
                    retryCount: retryCount,
                    sleepDurationProvider: (retryNumber) =>
                        TimeSpan.FromMilliseconds(Math.Pow(exponentialBackoffPow, retryNumber) * sleepDuration)
                );
        }
    }
}
