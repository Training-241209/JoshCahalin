import { axiosInstance } from "@/lib/axios-config";
import { useQuery, UseQueryResult } from "@tanstack/react-query";
import { useRouter } from "@tanstack/react-router";

export function useAuth(): UseQueryResult<{ email: string }> {
  const router = useRouter();

  return useQuery({
    queryKey: ["auth"],
    queryFn: async () => {
      try {
        console.log("Attempting to fetch authenticated user...");
        const resp = await axiosInstance.get("/me");
        console.log("Response Status:", resp.status);
        console.log("Response Data:", resp.data);
        return resp.data;
      } catch (e) {
        console.error(e);
        console.error("Error fetching authenticated user:", e);
      
        router.navigate({ to: "/auth/login" });
        return null;
      }
    },
    staleTime: 1000 * 60 * 5, // 5 mins
    gcTime: 1000 * 60 * 10, // 10 mins
    refetchOnWindowFocus: false,
    refetchOnReconnect: false,
  });
}
